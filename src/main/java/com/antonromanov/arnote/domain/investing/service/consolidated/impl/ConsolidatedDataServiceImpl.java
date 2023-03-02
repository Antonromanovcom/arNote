package com.antonromanov.arnote.domain.investing.service.consolidated.impl;

import com.antonromanov.arnote.domain.finplanning.common.service.globalcache.GlobalCache;
import com.antonromanov.arnote.domain.finplanning.goal.repositoty.GoalsRepo;
import com.antonromanov.arnote.domain.finplanning.loan.mapper.LoanRqMapper;
import com.antonromanov.arnote.domain.finplanning.loan.mapper.LoanRsMapper;
import com.antonromanov.arnote.domain.investing.dto.common.InvestingFilterMode;
import com.antonromanov.arnote.domain.investing.dto.response.ConsolidatedInvestmentDataRs;
import com.antonromanov.arnote.domain.investing.repository.BondsRepo;
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
import java.util.Map;

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


    @Override
    public ConsolidatedInvestmentDataRs getConsolidatedData(String filter, String sort) {

        ArNoteUser user = userService.getUserFromPrincipal();

        user = userService.checkAndSaveUserSettings(user, new HashMap<UserSettingType, String>() {{
            put(UserSettingType.INVEST_FILTER, filter);
            put(UserSettingType.INVEST_SORT, sort); //todo: считаю это нужно вынести в отдельный метод
        }});




        return ConsolidatedInvestmentDataRs.builder()
                .bonds(bondsRepo.findAllByUser(user)
                        .stream()
                        .map(bond -> prepareBondRs(bond, finalUser))
                        .filter(user.getInvestingFilterMode() != null ? complexPredicate(user.getInvestingFilterMode()) :
                                s -> s.getTicker() != null)
                        .sorted(user.getInvestingSortMode() == null ? InvestingSortMode.NONE.getComparator() :
                               user.getInvestingSortMode().getComparator())
                        .collect(Collectors.toList()))
                .build();
    }
}
