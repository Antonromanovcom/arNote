package com.antonromanov.arnote.service.investment.calc.bonds;

import com.antonromanov.arnote.model.ArNoteUser;
import com.antonromanov.arnote.model.investing.Bond;
import com.antonromanov.arnote.model.investing.Purchase;
import com.antonromanov.arnote.model.investing.cache.enums.CacheDictType;
import com.antonromanov.arnote.model.investing.response.ConsolidatedDividendsRs;
import com.antonromanov.arnote.model.investing.response.DividendRs;
import com.antonromanov.arnote.model.investing.response.enums.Currencies;
import com.antonromanov.arnote.model.investing.external.requests.MoexRestTemplateOperation;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexDocumentRs;
import com.antonromanov.arnote.model.investing.response.xmlpart.currentquote.MoexRowsRs;
import com.antonromanov.arnote.service.investment.cache.CacheService;
import com.antonromanov.arnote.service.investment.requestservice.RequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import static com.antonromanov.arnote.utils.ArNoteUtils.isInteger;

/**
 * Расчет расчета различных данных по облигациям.
 */
@Service
@Slf4j
public class BondServiceImpl implements BondCalcService {

    private final List<String> BOARD_GROUP_LIST = Arrays.asList("58", "193", "7", "67", "207");
    private final RequestService httpClient;
    private final CacheService cacheService;
  //  private final SharesCalcService sharesCalcService;

    public BondServiceImpl(RequestService httpClient, CacheService cacheService/*, SharesCalcService sharesCalcService*/) {
        this.httpClient = httpClient;
        this.cacheService = cacheService;
    //    this.sharesCalcService = sharesCalcService;
    }

    /**
     * Подготовить список купонов в формате списка дивидендов.
     *
     * @return
     */
    @Override
    public List<DividendRs> prepareCouponList(MoexRowsRs bondData) {

        List<DividendRs> resultList = new ArrayList<>();

        if (bondData != null && bondData.getCouponPeriod() != null && isInteger(bondData.getCouponPeriod())) {
            int couponsCountPerYear = Math.toIntExact(365 / Integer.parseInt(bondData.getCouponPeriod()));

            resultList.add(DividendRs.builder()
                    .value(Double.valueOf(bondData.getCouponValue()))
                    .currencyId(Currencies.search(bondData.getCurrencyId()))
                    .registryCloseDate(bondData.getNextCoupon())
                    .build());

            for (int i = 1; i < couponsCountPerYear; i++) {
                resultList.add(DividendRs.builder()
                        .value(Double.valueOf(bondData.getCouponValue()))
                        .currencyId(Currencies.search(bondData.getCurrencyId()))
                        .registryCloseDate(LocalDate.parse(bondData.getNextCoupon())
                                .plusDays(Integer.parseInt(bondData.getCouponPeriod())).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .build());
            }
            return resultList;
        } else {
            return null;
        }
    }

    /**
     * Получить купоны по облигации.
     *
     * @return
     */
    @Override
    public ConsolidatedDividendsRs getCoupons(Bond bond, ArNoteUser user) {

        return ConsolidatedDividendsRs.builder()
                .dividendList(prepareCouponList(getBondDataByTicker(bond.getTicker()).orElse(null)))
                .divSum((365 / getBondDataByTicker(bond.getTicker())
                        .map(MoexRowsRs::getCouponPeriod)
                        .map(Double::parseDouble).orElse(1D)) * ((getBondDataByTicker(bond.getTicker())
                        .map(MoexRowsRs::getCouponValue)
                        .map(Double::parseDouble)
                        .map(v -> (int) Math.round(v))
                        .orElse(0))))
                .percent(getBondDataByTicker(bond.getTicker())
                        .map(MoexRowsRs::getCouponPercent)
                        .map(Double::parseDouble)
                        .orElse(0D))
                .build();
    }

    /**
     * Получить минимальный лот облигации или сколько куплено уже.
     *
     * @return
     */
    @Override
    public Integer getBondLot(Bond bond, ArNoteUser user, List<Purchase> purchaseList) {

        if (!bond.getIsBought()) { // если это план по облигации
            return getBondDataByTicker(bond.getTicker())
                    .map(MoexRowsRs::getLotSize)
                    .map(Integer::parseInt).orElse(0);
        } else { // а если есть реальные покупки по облигации
            /*
             * Считаем сумму покупок (сколько всего купили бумаг то)
             */
            return purchaseList.stream()
                    .map(Purchase::getLot)
                    .reduce(0, Integer::sum);
        }
    }

    /**
     * Получить текущую валюту облигации
     *
     * @param ticker - тикер бумаги.
     * @return
     */
    @Override
    public Currencies getBondCurrency(String ticker) {
        return getBondDataByTicker(ticker)
                .map(MoexRowsRs::getCurrencyId)
                .map(Currencies::search)
                .orElse(Currencies.RUB);

    }

    /**
     * Получить имя облигации.
     *
     * @param ticker
     * @return
     */
    @Override
    public Optional<String> getBondName(String ticker) {
        return getBondDataByTicker(ticker).map(MoexRowsRs::getSecName);
    }

    /**
     * Получить текущую цену облигации
     *
     * @param ticker - тикер бумаги.
     * @return
     */
    @Override
    public Double getCurrentBondPrice(String ticker) {
        /*return getBondDataByTicker(ticker)
                .map(p -> (
                        (Double.parseDouble(p.getLotValue()) * Double.parseDouble(p.getPrevLegalClosePrice())) / 100)
                        * sharesCalcService.getCurrencyMultiplier(p.getCurrencyId())).orElse(0D);*/

        return null;

    }

    /**
     * Запросить Облигацию по тикеру.
     *
     * @param ticker - тикер
     * @return
     */
    @Override
    public Optional<MoexRowsRs> getBondDataByTicker(String ticker) {

        return getBonds()
                .getData()
                .getRow()
                .stream()
                .filter(b -> ticker.equals(b.getSecid()))
                .findFirst();
    }

    /**
     * Подготовить финальную цену (цена * лот).
     *
     * @param bond
     * @param user
     * @return
     */
    @Override
    public Integer calculateFinalPrice(Bond bond, ArNoteUser user) {
            if (bond.getIsBought()) { // если это ФАКТ
                return bond.getPurchaseList().stream()
                        .map(p -> p.getLot() * p.getPrice())
                        .reduce((double) 0, Double::sum).intValue();
            } else { // если ПЛАН
                return (getBondLot(bond, user, bond.getPurchaseList()))
                        * (getCurrentBondPrice(bond.getTicker()).intValue());
            }


    }

    /**
     * Запросить Облигации по всем доскам сразу.
     *
     * @return
     */
    @Override
    public MoexDocumentRs getBonds() {

        Iterator<String> it = BOARD_GROUP_LIST.iterator();
        MoexDocumentRs result = new MoexDocumentRs();
        while (it.hasNext()) {
            if (result.getData() == null) {
                result = getBondsByBoardGroup(it.next());
            } else {
                result.getData().getRow().addAll((getBondsByBoardGroup(it.next())).getData().getRow());
            }
        }
        return result;
    }

    /**
     * Запросить Облигации.
     *
     * @return
     */
    @Override
    public MoexDocumentRs getBondsByBoardGroup(String boardGroup) {
        if (cacheService.checkDict(CacheDictType.BONDS_BY_BOARD_ID, boardGroup)) { //todo: посмотреть можно ли все-таки написать аннотацию под это дело самому
            return cacheService.getDict(CacheDictType.BONDS_BY_BOARD_ID, boardGroup);
        } else {
            MoexDocumentRs doc = (MoexDocumentRs) httpClient
                    .sendAndMarshall(MoexRestTemplateOperation.GET_BONDS, boardGroup, null);
            cacheService.putToCache(CacheDictType.BONDS_BY_BOARD_ID, boardGroup, doc, MoexDocumentRs.class);
            return doc;
        }
    }
}
