package com.antonromanov.arnote.service.investment.calc;

import com.antonromanov.arnote.model.LocalUser;
import com.antonromanov.arnote.model.investing.response.ConsolidatedDividendsRs;
import com.antonromanov.arnote.model.investing.response.DeltaRs;
import com.antonromanov.arnote.model.investing.response.enums.Targets;
import com.antonromanov.arnote.repositoty.BondsRepo;
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
    public Optional<Long> getTotalInvestment(LocalUser user) {
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
    public Optional<Long> getSharesDelta(LocalUser user) {
        return Optional.of(repo.findAllByUser(user).stream()
                .map(b -> {
                    DeltaRs deltaRs = calcService
                            .calculateDelta(calcService.getBoardId(b.getTicker()), b.getTicker(),
                                    calcService.getCurrentQuoteByTicker(b.getTicker()
                                    ).orElse((double) 0),
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
    public Optional<Long> getTotalDivsReturn(LocalUser user) {
        return Optional.of(repo.findAllByUser(user).stream()
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
    public Long calculateRequiredInvestments(LocalUser user, Targets target) {
        return Optional.of((getTotalInvestment(user).orElse(0L) * (target.getValue() * 12)) / calculateTotalReturns(user)).orElse(0L);
    }

    /**
     * Посчитать общую сумму прибыли.
     *
     * @param user
     * @return
     */
    @Override
    public Long calculateTotalReturns(LocalUser user) {
        return (getSharesDelta(user).orElse(0L)) + 1L + getTotalDivsReturn(user).orElse(0L);
    }
}
