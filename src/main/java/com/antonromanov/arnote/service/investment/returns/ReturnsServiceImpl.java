package com.antonromanov.arnote.service.investment.returns;

import com.antonromanov.arnote.model.ArNoteUser;
import com.antonromanov.arnote.model.investing.BondType;
import com.antonromanov.arnote.model.investing.response.ConsolidatedDividendsRs;
import com.antonromanov.arnote.model.investing.response.DeltaRs;
import com.antonromanov.arnote.model.investing.response.enums.Targets;
import com.antonromanov.arnote.repositoty.BondsRepo;
import com.antonromanov.arnote.service.investment.calc.CalculateService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReturnsServiceImpl implements ReturnsService {

    private final CalculateService calcService;
    private final BondsRepo repo;

    public ReturnsServiceImpl(BondsRepo repo, CalculateService calcService) {
        this.calcService = calcService;
        this.repo = repo;
    }

    @Override
    public Optional<Long> getTotalInvestment(ArNoteUser user) {
        return Optional.of(repo.findAllByUser(user).stream()
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
    public Optional<Long> getSharesDelta(ArNoteUser user) {
        return Optional.of(repo.findAllByUser(user).stream()
                .filter(bond->bond.getType()== BondType.SHARE)
                .map(b -> {
                    DeltaRs deltaRs = calcService
                            .calculateDelta(calcService.getBoardId(b.getTicker()), b.getTicker(),
                                    calcService.getCurrentQuoteByTicker(b.getTicker()).orElse((double) 0),
                                    b.getPurchaseList());

                    return deltaRs.getDeltaInRubles();
                })
                .reduce((long) 0, Long::sum));
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
                    ConsolidatedDividendsRs divs = calcService.getDividends(b, user);
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
     * Посчитать общую сумму прибыли.
     *
     * @param user
     * @return
     */
    @Override
    public Long calculateTotalReturns(ArNoteUser user) {
        return (getSharesDelta(user).orElse(0L)) + 1L + getTotalDivsReturn(user).orElse(0L);
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
                    ConsolidatedDividendsRs divs = calcService.getCoupons(b, user);
                    return divs.getDivSum();
                })
                .reduce((double) 0, Double::sum))
                .map(Math::round);
    }
}
