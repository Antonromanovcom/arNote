package com.antonromanov.arnote.domain.investing.service.returns;

import com.antonromanov.arnote.domain.investing.dto.common.BondType;
import com.antonromanov.arnote.domain.investing.dto.response.ConsolidatedDividendsRs;
import com.antonromanov.arnote.domain.investing.dto.response.DeltaRs;
import com.antonromanov.arnote.domain.investing.dto.response.enums.Targets;
import com.antonromanov.arnote.domain.investing.entity.Bond;
import com.antonromanov.arnote.domain.investing.repository.BondsRepo;
import com.antonromanov.arnote.domain.investing.service.calc.CommonService;
import com.antonromanov.arnote.domain.investing.service.calc.bonds.BondCalcService;
import com.antonromanov.arnote.domain.user.service.UserService;
import com.antonromanov.arnote.old.model.ArNoteUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReturnsServiceImpl implements ReturnsService {

    /*private final BondCalcService bondCalcService;
    private final BondsRepo repo;
    private final CommonService commonService;*/
    private final UserService userService;
    private final BondsRepo repo;
    private final CommonService commonService;
    private final BondCalcService bondCalcService;

    /*public ReturnsServiceImpl(BondsRepo repo, BondCalcService bondCalcService, CommonService commonService) {
        this.repo = repo;
        this.bondCalcService = bondCalcService;
        this.commonService = commonService;
    }*/

    /**
     * Запросить общую сумму инвестированного.
     *
     * @return
     */
    @Override
    public Optional<Long> getTotalInvestment() {
        ArNoteUser user = userService.getUserFromPrincipal();
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
     * @return
     */
    @Override
    public Optional<Double> getSharesDelta() {
        ArNoteUser user = userService.getUserFromPrincipal();
        return Optional.of(repo.findAllByUser(user).stream()
                .filter(bond->bond.getType()== BondType.SHARE)
                .map(b -> {
                    DeltaRs deltaRs = commonService.prepareDelta(b);
                    return deltaRs==null ? 0 : deltaRs.getTinkoffDelta();
                }).reduce((double) 0, Double::sum));
    }

    @Override
    public Optional<Double> getSharesDeltaForBought() {
        ArNoteUser user = userService.getUserFromPrincipal();
        return Optional.of(repo.findAllByUser(user).stream()
                .filter(bond->bond.getType()== BondType.SHARE)
                .filter(Bond::getIsBought)
                .map(b -> {
                    DeltaRs deltaRs = commonService.prepareDelta(b);
                    return deltaRs==null ? 0 : deltaRs.getTinkoffDelta();
                }).reduce((double) 0, Double::sum));
    }

    /**
     * Получить общую доходность по дивидендам.
     *
     * @return
     */
    @Override
    public Optional<Long> getTotalDivsReturn() {
        ArNoteUser user = userService.getUserFromPrincipal();
        return Optional.of(repo.findAllByUser(user).stream()
                .filter(bond -> bond.getType()==BondType.SHARE)
                .filter(Bond::getIsBought)
                .map(b -> {
                    ConsolidatedDividendsRs divs = commonService.getDivsOrCoupons(b);
                    return divs.getDivSum();
                })
                .reduce((double) 0, Double::sum))
                .map(Math::round);
    }

  /*  @Override
    public List<DivsDebug> getDivsDebug(ArNoteUser user) {
        return repo.findAllByUser(user).stream()
                .filter(bond -> bond.getType()==BondType.SHARE)
                .filter(Bond::getIsBought)
                .map(b -> {
                    ConsolidatedDividendsRs divsData = commonService.getDivsOrCoupons(b, user);
                    List<DividendRs> divList = new ArrayList<>();

                    if (divsData!=null && divsData.getDividendList().size()>0) {

                        divList.add(DividendRs.builder()
                                .value(divsData.getDividendList().stream()
                                        .map(DividendRs::getValue)
                                        .reduce((double) 0, Double::sum))
                                .currencyId(divsData.getDividendList().get(0).getCurrencyId())
                                .build());
                    }
                    return DivsDebug.builder()
                            .ticker(b.getTicker())
                            .divs(divList)
                            .build();
                }).collect(Collectors.toList());



    }*/

    /**
     * Посчитать сколько надо вложить для получения заданной ежемесячной прибыли.
     *
     * @return
     */
    @Override
    public Long calculateRequiredInvestments(Targets target) {
        try {
            return (getTotalInvestment().orElse(0L) * (target.getValue() * 12)) / calculateTotalReturns();
        } catch (Exception e){
            return 0L;
        }
    }

    /**
     * Посчитать общую сумму прибыли: рост по акциям + купоны облигаций + дивиденды.
     *
     * @return
     */
    @Override
    public Long calculateTotalReturns() {
        return (getSharesDeltaForBought().map(Double::longValue).orElse(0L)) +
                getTotalBondsReturns().orElse(0L) +
                getTotalDivsReturn().orElse(0L);
    }

    /**
     * Получить общий купонный доход по всем облигациям пользователя.
     *
     * @return
     */
    @Override
    public Optional<Long> getTotalBondsReturns() {
        ArNoteUser user = userService.getUserFromPrincipal();
        return Optional.of(repo.findAllByUser(user).stream()
                .filter(bond -> bond.getType()==BondType.BOND)
                .filter(Bond::getIsBought)
                .map(b -> {
                    ConsolidatedDividendsRs divs = bondCalcService.getCoupons(b, user);
                    return divs.getDivSum();
                })
                .reduce((double) 0, Double::sum))
                .map(Math::round);
    }
}
