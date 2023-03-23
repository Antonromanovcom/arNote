package com.antonromanov.arnote.domain.finplanning.income.service.impl;

import com.antonromanov.arnote.domain.finplanning.income.dto.rq.IncomeRq;
import com.antonromanov.arnote.domain.finplanning.income.entity.Income;
import com.antonromanov.arnote.domain.finplanning.income.mapper.IncomeMapperRq;
import com.antonromanov.arnote.domain.finplanning.income.repo.IncomeRepo;
import com.antonromanov.arnote.domain.finplanning.income.service.IncomeService;
import com.antonromanov.arnote.domain.user.service.UserService;
import com.antonromanov.arnote.domain.finplanning.common.dto.rs.FinalBalanceCalculationsRs;
import com.antonromanov.arnote.domain.finplanning.income.dto.rq.IdListRq;
import com.antonromanov.arnote.domain.finplanning.income.dto.rq.IncomesForDeleteRq;
import com.antonromanov.arnote.domain.finplanning.common.dto.rs.SingleOperationRs;
import com.antonromanov.arnote.domain.user.entity.ArNoteUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import static com.antonromanov.arnote.old.utils.Utils.dateToLocalDate;

@Service
@AllArgsConstructor
public class IncomeServiceImpl implements IncomeService {

    private final UserService userService;
    private final IncomeRepo incomeRepo;
    private final IncomeMapperRq mapper;

    @Override
    public List<Income> incomesForCurrentDate(int year, int month) {
        ArNoteUser user = userService.getUserFromPrincipal();
        List<Income> allIncomesByUser = incomeRepo.findAllByUserOrderByIncomeDateAsc(user); // все доходы юзера
        return allIncomesByUser.stream()
                .filter(i -> dateToLocalDate(i.getIncomeDate()).getYear() == year) // фильтруем по году
                .filter(i -> dateToLocalDate(i.getIncomeDate()).getMonthValue() == month)
                .collect(Collectors.toList());
    }

    @Override
    public int getCurrentIncome(int year, int month) {
        return (incomesForCurrentDate(year, month)).stream()
                .findFirst()
                .map(Income::getIncome)
                .orElse(0);
    }

    @Override
    public Integer getPreviousIncome(int curYear, int curMonth, LinkedHashMap<LocalDate, FinalBalanceCalculationsRs> globalBalanceMap) {
        int calculatedMonth = LocalDate.of(curYear, curMonth, 1).minusMonths(1).getMonthValue();
        int calculatedYear = LocalDate.of(curYear, curMonth, 1).minusMonths(1).getYear();
        return globalBalanceMap.entrySet().stream()
                .filter(ld -> ld.getKey().getYear() == calculatedYear && ld.getKey().getMonthValue() == calculatedMonth)
                .map(Map.Entry::getValue)
                .findFirst()
                .map(FinalBalanceCalculationsRs::getBalance)
                .orElse(0);
    }

    @Override
    public SingleOperationRs addIncome(IncomeRq payload) {
        ArNoteUser user = userService.getUserFromPrincipal();
        Income newIncome = incomeRepo.saveAndFlush(mapper.map(payload, user));
        return SingleOperationRs.builder()
                .id(newIncome.getId())
                .build();
    }

    @Override
    public SingleOperationRs deleteIncome(IncomesForDeleteRq req) {
        ArNoteUser user = userService.getUserFromPrincipal();
        req.getIdList().forEach(t -> incomeRepo.findIncomeByUserAndId(user, t.getId()).ifPresent(incomeRepo::delete));
        // todo: после того как разберемся точно ли нам нужен список айдишников (а не один), нам бы надо чтобы какая-то ошибка выскакивала, если айдишника  /айдишников нет
        return SingleOperationRs
                .builder()
                .id(req.getIdList().stream().findFirst().map(IdListRq::getId).orElse(null))
                .build();
    }

    @Override
    public SingleOperationRs editIncome(IncomeRq payload) {

        ArNoteUser arNoteUser = userService.getUserFromPrincipal();

            int year = dateToLocalDate(payload.getIncomeDate()).getYear();
            int month = dateToLocalDate(payload.getIncomeDate()).getMonthValue();
            Optional<Income> existIncome = incomeRepo.findIncomeByUserAndMonthAndYear(arNoteUser, year, month).stream()
                    .findFirst();
            existIncome.ifPresent(income -> incomeRepo.save(mapper.map(payload, arNoteUser)));
        return SingleOperationRs
                .builder()
                .id(payload.getId())
                .build();
    }
}
