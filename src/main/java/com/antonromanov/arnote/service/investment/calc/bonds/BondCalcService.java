package com.antonromanov.arnote.service.investment.calc.bonds;

import com.antonromanov.arnote.model.ArNoteUser;
import com.antonromanov.arnote.domain.investing.dto.common.Bond;
import com.antonromanov.arnote.domain.investing.dto.common.Purchase;
import com.antonromanov.arnote.domain.investing.dto.response.ConsolidatedDividendsRs;
import com.antonromanov.arnote.domain.investing.dto.response.DividendRs;
import com.antonromanov.arnote.domain.investing.dto.response.enums.Currencies;
import com.antonromanov.arnote.domain.investing.dto.response.xmlpart.currentquote.MoexDocumentRs;
import com.antonromanov.arnote.domain.investing.dto.response.xmlpart.currentquote.MoexRowsRs;
import java.util.List;
import java.util.Optional;

/**
 * Расчетный сервис с операциями по облигациям.
 */
public interface BondCalcService {
    /**
     * Подготовить список купонов в формате списка дивидендов.
     *
     * @return
     */
    List<DividendRs> prepareCouponList(MoexRowsRs bondData);

    /**
     * Получить имя облигации.
     *
     * @param ticker
     * @return
     */
    Optional<String> getBondName(String ticker);

    /**
     * Получить минимальный лот облигации или сколько куплено уже.
     *
     * @return
     */
    Integer getBondLot(Bond bond, ArNoteUser user, List<Purchase> purchaseList);

    /**
     * Получить купоны по облигации.
     *
     * @return
     */
    ConsolidatedDividendsRs getCoupons(Bond bond, ArNoteUser user);

    /**
     * Получить текущую цену облигации
     *
     * @param ticker - тикер бумаги.
     * @return
     */
    Double getCurrentBondPrice(String ticker);

    /**
     * Получить текущую валюту облигации
     *
     * @param ticker - тикер бумаги.
     * @return
     */
    Currencies getBondCurrency(String ticker);

    /**
     * Запросить Облигации.
     *
     * @return
     */
    MoexDocumentRs getBondsByBoardGroup(String boardGroup);

    /**
     * Запросить Облигации по всем доскам сразу.
     *
     * @return
     */
    MoexDocumentRs getBonds();

    /**
     * Запросить Облигацию по тикеру.
     *
     * @param ticker - тикер
     * @return
     */
    Optional<MoexRowsRs> getBondDataByTicker(String ticker);

    /**
     * Подготовить финальную цену (цена * лот).
     *
     * @param bond
     * @param user
     * @return
     */
    Integer calculateFinalPrice(Bond bond, ArNoteUser user);
}
