package com.antonromanov.arnote.domain.investing.service.consolidated.impl;

import com.antonromanov.arnote.domain.finplanning.common.service.globalcache.GlobalCache;
import com.antonromanov.arnote.domain.finplanning.goal.repositoty.GoalsRepo;
import com.antonromanov.arnote.domain.finplanning.loan.mapper.LoanRqMapper;
import com.antonromanov.arnote.domain.finplanning.loan.mapper.LoanRsMapper;
import com.antonromanov.arnote.domain.investing.dto.common.Bond;
import com.antonromanov.arnote.domain.investing.dto.response.BondRs;
import com.antonromanov.arnote.domain.investing.dto.response.ConsolidatedInvestmentDataRs;
import com.antonromanov.arnote.domain.investing.repository.BondsRepo;
import com.antonromanov.arnote.domain.investing.service.calc.CommonService;
import com.antonromanov.arnote.domain.investing.service.consolidated.ConsolidatedDataService;
import com.antonromanov.arnote.domain.user.repository.UsersRepo;
import com.antonromanov.arnote.domain.user.service.UserService;
import com.antonromanov.arnote.domain.wish.enums.UserSettingType;
import com.antonromanov.arnote.old.model.ArNoteUser;
import com.antonromanov.arnote.old.model.investing.InvestingSortMode;
import com.antonromanov.arnote.old.repositoty.CreditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.stream.Collectors;

import static com.antonromanov.arnote.old.utils.ArNoteUtils.complexPredicate;

@Service
@AllArgsConstructor
public class ConsolidatedDataServiceImpl implements ConsolidatedDataService {

    private final UserService userService;
    private final CreditRepository creditRepo;
    private final GlobalCache globalCache;
    private final LoanRqMapper rqMapper;
    private final LoanRsMapper rsMapper;
    private GoalsRepo purchaseRepo;
    private UsersRepo usersRepo;
    private BondsRepo bondsRepo;

    private final CommonService commonService;


    @Override
    public ConsolidatedInvestmentDataRs getConsolidatedData(String filter, String sort) {

        ArNoteUser user = userService.getUserFromPrincipal();

        user = userService.checkAndSaveUserSettings(user, new HashMap<>() {{
            put(UserSettingType.INVEST_FILTER, filter);
            put(UserSettingType.INVEST_SORT, sort); //todo: считаю это нужно вынести в отдельный метод
        }});


        return ConsolidatedInvestmentDataRs.builder()
                .bonds(bondsRepo.findAllByUser(user)
                        .stream()
                        .map(this::prepareBondRs)
                        .filter(user.getInvestingFilterMode() != null ? complexPredicate(user.getInvestingFilterMode()) :
                                s -> s.getTicker() != null)
                        .sorted(user.getInvestingSortMode() == null ? InvestingSortMode.NONE.getComparator() :
                               user.getInvestingSortMode().getComparator())
                        .collect(Collectors.toList()))
                .build();
    }

    /**
     * Подготовить респонс бумаги.
     *
     * @param bond - данные по бумаге из БД.
     * @return
     */
    private BondRs prepareBondRs(Bond bond) {

        return BondRs.builder()
                .id(bond.getId())
                .ticker(bond.getTicker())
                .type(bond.getType().name())
                .isBought(bond.getIsBought())
                .stockExchange(bond.getStockExchange().name())
                .currentPrice(commonService.prepareCurrentPrice(bond))
                .currency(commonService.getCurrency(bond))
                .dividends(commonService.getDivsOrCoupons(bond))
                .minLot(commonService.getLot(bond))
                .finalPrice(commonService.getFinalPrice(bond))
                .delta(commonService.prepareDelta(bond))
                .description(commonService.getDescription(bond))
                .build();
    }
}
