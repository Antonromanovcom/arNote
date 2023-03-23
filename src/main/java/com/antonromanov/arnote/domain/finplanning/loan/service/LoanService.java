package com.antonromanov.arnote.domain.finplanning.loan.service;

import com.antonromanov.arnote.domain.finplanning.loan.dto.rq.CreditRq;
import com.antonromanov.arnote.domain.finplanning.loan.dto.rs.*;
import com.antonromanov.arnote.domain.finplanning.loan.dto.transfer.CalculatedLoansTableTr;
import com.antonromanov.arnote.domain.finplanning.loan.entity.Credit;
import com.antonromanov.arnote.domain.finplanning.loan.dto.rq.LoanByDateRq;
import com.antonromanov.arnote.common.exceptions.UserNotFoundException;
import com.antonromanov.arnote.domain.user.entity.ArNoteUser;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LoanService {


    /**
     * Добавить кредит.
     *
     * @param request
     * @return
     */
    OperateCreditRs addLoan(CreditRq request);

    /**
     * Подсчитать консолидированную таблицу по кредитам.
     *
     * @param user
     * @return
     */
    CalculatedLoansTableTr getCalculatedLoansTable(ArNoteUser user);

    /**
     * Удалить кредит.
     *
     * @param id
     * @return
     */
    OperateCreditRs deleteLoan(Long id);

    /**
     * Запросить список кредитов.
     *
     * @return
     */
    FullLoansListRs getFullLoansList();

    /**
     * Вернуть все кредиты по пользаку.
     *
     * @param user
     * @return
     */
    List<Credit> getAllCredits(ArNoteUser user);

    /**
     * Редактировать кредит.
     *
     * @param payload
     * @return
     */
    OperateCreditRs editLoan(CreditRq payload);


    /**
     * Получить данные по кредиту по ID.
     *
     * @param id
     * @return
     */
    CreditRs getLoanById(Long id);


    FullLoansListRs getLoanByDate(LoanByDateRq payload);

    /**
     * Получить список ЗП по пользаку.
     *
     * @param principal
     * @return
     */
    //  SalaryListRs getSalariesList(Principal principal) throws UserNotFoundException;

    /**
     * Редактировать зарплату.
     *
     * @param principal
     * @param payload
     * @return
     */
    //  SingleOperationRs editSalary(Principal principal, SalaryRq payload) throws UserNotFoundException;

    /**
     * Добавить новую ЗП.
     *
     * @param principal
     * @param payload
     * @return
     */
    //  SingleOperationRs addSalary(Principal principal, SalaryRq payload);

    /**
     * Удалить ЗП.
     *
     * @param principal
     * @param id
     * @return
     */
    //  SingleOperationRs deleteSalary(Principal principal, Long id);


    /**
     * Получить свободные слоты по кредитам.
     *
     * @param payload
     * @return
     */
    FreeLoanSlotsRs getLoansSlots(String payload) throws UserNotFoundException;

    /**
     * Посчитать дату выплаты самого последнего кредита.
     *
     * @return
     */
    Optional<LocalDate> getLastCreditDate(ArNoteUser user);

    /**
     * Платежи по кредитам по состоянию на конкретную дату.
     *
     * @param curMonth
     * @param curYear
     * @return
     */
    Integer getLoanPaymentsByDate(int curYear, int curMonth);


    /**
     * Получить и рассчитать текущие кредиты отфильтрованные по текущей дате.
     *
     * @param year
     * @param month
     * @return
     */
    CreditListRs getCreditsFiltered(Integer year, Integer month);


}
