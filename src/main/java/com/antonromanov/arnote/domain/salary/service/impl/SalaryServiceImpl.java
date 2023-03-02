package com.antonromanov.arnote.domain.salary.service.impl;

import com.antonromanov.arnote.domain.finplanning.common.dto.rs.SingleOperationRs;
import com.antonromanov.arnote.domain.salary.dto.rq.SalaryRq;
import com.antonromanov.arnote.domain.salary.dto.rs.SalaryListRs;
import com.antonromanov.arnote.domain.salary.dto.rs.SalaryRs;
import com.antonromanov.arnote.domain.salary.repository.SalaryRepository;
import com.antonromanov.arnote.domain.salary.service.SalaryService;
import com.antonromanov.arnote.domain.salary.mapper.SalaryMapper;
import com.antonromanov.arnote.domain.user.service.UserService;
import com.antonromanov.arnote.old.entity.common.Salary;
import com.antonromanov.arnote.old.exceptions.FinPlanningException;
import com.antonromanov.arnote.old.exceptions.NoDataYetException;
import com.antonromanov.arnote.old.exceptions.enums.ErrorCodes;
import com.antonromanov.arnote.old.model.ArNoteUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SalaryServiceImpl implements SalaryService {

    private final SalaryMapper mapper;
    private final UserService userService;
    private final SalaryRepository salaryRepository;

    @Override
    public Integer getLastSalary(ArNoteUser user) {
        return salaryRepository.getLastSalaryListByUserDesc(user).stream().findFirst()
                .map(Salary::getResidualSalary).orElse(0);
    }

    @Override
    public Optional<Salary> getLastSalaryListByUserDesc(ArNoteUser user) {
        return salaryRepository.getLastSalaryListByUserDesc(user).stream().findFirst();
    }

    /**
     * Получить ближайшую к дате ЗП по пользователю.
     *
     * @param year      - год.
     * @param currMonth - месяц.
     * @param
     * @return
     */
    @Override
    public Optional<Salary> getClosestSalary(int year, int currMonth) {
        LocalDateTime resultTime;
        ArNoteUser user = userService.getUserFromPrincipal();
        List<Salary> salaryListByUser = salaryRepository.getLastSalaryListByUserDesc(user);
        if (salaryListByUser.size() > 0) {

            try {

                if (isUserDateBeforeClosestTimeStamp(year, currMonth, salaryListByUser)) {
                    resultTime = (getSortedDateTree(salaryListByUser))
                            .ceiling(LocalDateTime.of(year, currMonth, 1, 0, 0));
                } else {
                    resultTime = (getSortedDateTree(salaryListByUser))
                            .floor(LocalDateTime.of(year, currMonth, 1, 0, 0));
                }

                if (resultTime == null) {
                    return Optional.empty();
                }

                return salaryListByUser.stream()
                        .filter(v -> v.getSalaryTimeStamp().isEqual(resultTime))
                        .findFirst();

            } catch (Exception e) {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Integer getMonthlySpending(int year, int currMonth) {
        return getClosestSalary(year, currMonth)
                .map(Salary::getLivingExpenses)
                .orElse(0); // средние ежемесячные расходы
    }

    @Override
    public SalaryListRs getSalariesList() {

        ArNoteUser arNoteUser = userService.getUserFromPrincipal();
        List<Salary> salaryList = salaryRepository.getLastSalaryListByUserDesc(arNoteUser);

        if (salaryList.isEmpty()) {
            throw new NoDataYetException(ErrorCodes.ERR_O2);
        } else {
            return SalaryListRs.builder()
                    .salariesList(salaryList.stream()
                            .map(mapper::mapSalaryRs)
                            .collect(Collectors.toList()))
                    .build();
        }
    }

    @Override
    public SingleOperationRs editSalary(SalaryRq payload) {

        ArNoteUser user = userService.getUserFromPrincipal();
        Optional<Salary> existSalary = salaryRepository.findSalaryById(payload.getId());
        existSalary.ifPresent(salary -> salaryRepository.save(mapper.mapForUpdateSalaryRq(payload, salary, user)));
        return existSalary
                .map(v -> SingleOperationRs.builder()
                        .id(payload.getId())
                        .build())
                .orElseThrow(() -> new FinPlanningException(ErrorCodes.ERR_O2));

    }

    @Override
    public SingleOperationRs deleteSalary(Long id) {

        Optional<Salary> salary = salaryRepository.findSalaryById(id);
        salary.ifPresent(salaryRepository::delete);
        return salary
                .map(v -> SingleOperationRs.builder()
                        .id(v.getId())
                        .build())
                .orElseThrow(() -> new FinPlanningException(ErrorCodes.ERR_10));

    }


    private LocalDateTime getClosestSalaryTimeStamp(List<Salary> salaryListByUser) {
        return salaryListByUser.stream()
                .min(Comparator.comparing(Salary::getSalaryTimeStamp))
                .map(Salary::getSalaryTimeStamp)
                .orElseThrow(() -> new FinPlanningException(ErrorCodes.ERR_12));
    }

    private boolean isUserDateBeforeClosestTimeStamp(int year, int currMonth, List<Salary> salaryListByUser) {
        return ((LocalDateTime.of(year, currMonth, 1, 0, 0)).isBefore(getClosestSalaryTimeStamp(salaryListByUser)));
    }

    private NavigableSet<LocalDateTime> getSortedDateTree(List<Salary> salaryListByUser) {
        return salaryListByUser.stream()
                .map(Salary::getSalaryTimeStamp)
                .collect(Collectors.toCollection(TreeSet::new));
    }


    @Override
    public SalaryRs addSalary(SalaryRq request) {
        ArNoteUser user = userService.getUserFromPrincipal();
        return mapper.mapSalaryRs(salaryRepository.saveAndFlush(mapper.mapSalaryRq(request, user)));
    }
}