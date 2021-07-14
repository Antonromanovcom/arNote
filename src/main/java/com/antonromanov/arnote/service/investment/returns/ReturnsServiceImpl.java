package com.antonromanov.arnote.service.investment.returns;

import com.antonromanov.arnote.model.ArNoteUser;
import com.antonromanov.arnote.model.investing.Bond;
import com.antonromanov.arnote.model.investing.BondType;
import com.antonromanov.arnote.model.investing.response.ConsolidatedDividendsRs;
import com.antonromanov.arnote.model.investing.response.DeltaRs;
import com.antonromanov.arnote.model.investing.response.enums.StockExchange;
import com.antonromanov.arnote.model.investing.response.enums.Targets;
import com.antonromanov.arnote.repositoty.BondsRepo;
import com.antonromanov.arnote.service.investment.calc.CommonService;
import com.antonromanov.arnote.service.investment.calc.bonds.BondCalcService;
import com.antonromanov.arnote.service.investment.calc.shares.SharesCalcService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ReturnsServiceImpl implements ReturnsService {

    private final BondCalcService bondCalcService;
    private final BondsRepo repo;
    private final CommonService commonService;

    public ReturnsServiceImpl(BondsRepo repo, BondCalcService bondCalcService, CommonService commonService) {
        this.repo = repo;
        this.bondCalcService = bondCalcService;
        this.commonService = commonService;
    }

    /**
     * Запросить общую сумму инвестированного.
     * @param user - текущий авторизовавшийся пользователь
     * @return
     */
    @Override
    public Optional<Long> getTotalInvestment(ArNoteUser user) {
        return Optional.of(repo.findAllByUser(user).stream()
                .filter(Bond::getIsBought)
                .map(b -> b.getPurchaseList().stream()
                        .map(p -> p.getLot() * p.getPrice())
                        .reduce((double) 0, Double::sum))
                .reduce((double) 0, Double::sum)
                .longValue());
    }

    /**
     * Получить дельту по всем бумагам пользователя.
     *
     * @param user
     * @return
     */
    @Override
    public Optional<Double> getSharesDelta(ArNoteUser user) {
        return Optional.of(repo.findAllByUser(user).stream()
                .filter(bond->bond.getType()== BondType.SHARE)
                .map(b -> {
                    DeltaRs deltaRs = commonService.prepareDelta(b);
                    return deltaRs==null ? 0 : deltaRs.getTinkoffDelta();
                }).reduce((double) 0, Double::sum));
    }

    /**
     * Получить общую доходность по дивидендам.
     *
     * @param user
     * @return
     */
    @Override
    public Optional<Long> getTotalDivsReturn(ArNoteUser user) {
        return Optional.of(repo.findAllByUser(user).stream()
                .filter(bond -> bond.getType()==BondType.SHARE)
                .map(b -> {
                    ConsolidatedDividendsRs divs = commonService.getDivsOrCoupons(b, user);
                    return divs.getDivSum();
                })
                .reduce((double) 0, Double::sum))
                .map(Math::round);
    }

    /**
     * Посчитать сколько надо вложить для получения заданной ежемесячной прибыли.
     *
     * @param user
     * @return
     */
    @Override
    public Long calculateRequiredInvestments(ArNoteUser user, Targets target) {
        return Optional.of((getTotalInvestment(user).orElse(0L) * (target.getValue() * 12)) / calculateTotalReturns(user)).orElse(0L);
    }

    /**
     * Посчитать общую сумму прибыли: рост по акциям + купоны облигаций + дивиденды.
     *
     * @param user
     * @return
     */
    @Override
    public Long calculateTotalReturns(ArNoteUser user) {
        return (getSharesDelta(user).map(Double::longValue).orElse(0L)) + getTotalBondsReturns(user).orElse(0L) + getTotalDivsReturn(user).orElse(0L);
    }

    /**
     * Получить общий купонный доход по всем облигациям пользователя.
     * @param user
     * @return
     */
    @Override
    public Optional<Long> getTotalBondsReturns(ArNoteUser user) {
        return Optional.of(repo.findAllByUser(user).stream()
                .filter(bond -> bond.getType()==BondType.BOND)
                .map(b -> {
                    ConsolidatedDividendsRs divs = bondCalcService.getCoupons(b, user);
                    return divs.getDivSum();
                })
                .reduce((double) 0, Double::sum))
                .map(Math::round);
    }
}
