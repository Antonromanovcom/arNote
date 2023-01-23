package com.antonromanov.arnote.domain.finplanning.loan.service;

import com.antonromanov.arnote.domain.finplanning.loan.dto.rq.CreditRq;
import com.antonromanov.arnote.domain.finplanning.loan.dto.rs.OperateCreditRs;
import com.antonromanov.arnote.domain.finplanning.loan.dto.rs.FullLoansListRs;
import com.antonromanov.arnote.domain.finplanning.loan.dto.rs.FreeLoanSlotsRs;
import com.antonromanov.arnote.domain.finplanning.loan.dto.transfer.CalculatedLoansTableTr;
import com.antonromanov.arnote.domain.finplanning.loan.entity.Credit;
import com.antonromanov.arnote.old.dto.rq.LoanByDateRq;
import com.antonromanov.arnote.domain.finplanning.loan.dto.rs.CreditRs;
import com.antonromanov.arnote.old.exceptions.BadIncomeParameter;
import com.antonromanov.arnote.old.exceptions.UserNotFoundException;
import com.antonromanov.arnote.old.model.ArNoteUser;
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
    OperateCreditRs addLoan(CreditRq request) throws UserNotFoundException, BadIncomeParameter;

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
    FullLoansListRs getFullLoansList() throws UserNotFoundException;

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
     * @param credits
     * @return
     */
    Optional<LocalDate> getLastCreditDate(ArNoteUser user);


}
