package com.antonromanov.arnote.services;

import com.antonromanov.arnote.sbdfvjbsdf.dto.rq.*;
import com.antonromanov.arnote.sbdfvjbsdf.dto.rs.*;
import com.antonromanov.arnote.sbdfvjbsdf.dto.rs.FinalBalanceCalculationsRs;
import com.antonromanov.arnote.exceptions.UserNotFoundException;
import java.security.Principal;

public interface FinPlanService {
    /**
     * Запросить консолидированную таблицу из кэша.
     * @param principal
     * @return
     * @throws UserNotFoundException
     */
    FinPlanListRs getFinPlanTableFromCache(Principal principal) throws UserNotFoundException;

    /**
     * Запросить консолидированную таблицу из БД.
     * @param principal
     * @return
     * @throws UserNotFoundException
     */
    FinPlanListRs getFinPlanTableFromDb(Principal principal) throws UserNotFoundException;

    /**
     * Добавить кредит.
     *
     * @param principal
     * @param request
     * @return
     */
    AddCreditRs addCredit(Principal principal, CreditRq request) throws UserNotFoundException;

    /**
     * Удалить кредит.
     *
     * @param principal
     * @param id
     * @return
     */
    AddCreditRs deleteLoan(Principal principal, Long id) ;

    /**
     * Запросить список кредитов.
     *
     * @param principal
     * @return
     */
    FullLoansListRs getFullLoansList(Principal principal) throws UserNotFoundException;

    /**
     * Редактировать кредит.
     *
     * @param payload
     * @param principal
     * @return
     */
    AddCreditRs editLoan(CreditRq payload, Principal principal) throws UserNotFoundException;

    /**
     * Добавить доход
     *
     * @param payload
     * @param principal
     * @return
     */
    SingleOperationRs addIncome(IncomeRq payload, Principal principal) throws UserNotFoundException;

    /**
     * Удалить доход
     *
     * @param principal - юзер
     * @return
     */
    SingleOperationRs deleteIncome(Principal principal, IncomesForDeleteRq req) throws UserNotFoundException;

    /**
     * Редактировать доход.
     *
     * @param payload
     * @param principal
     * @return
     */
    SingleOperationRs editIncome(IncomeRq payload, Principal principal) throws UserNotFoundException;

    /**
     * Получить данные по кредиту по ID.
     * @param id
     * @param principal
     * @return
     */
    CreditRs getLoanById(Long id, Principal principal) throws UserNotFoundException;

    /**
     * Деталка по остаткам.
     * @param payload
     * @param principal
     * @return
     */
    FinalBalanceCalculationsRs getRemainsDetailInfo(GetRemainsDetailInfoRq payload, Principal principal) throws UserNotFoundException;

    SingleOperationRs addGoal(GoalRq payload, Principal principal) throws UserNotFoundException;

    FullLoansListRs getLoanByDate(LoanByDateRq payload, Principal principal);

    /**
     * Редактировать цель.
     *
     * @param payload
     * @param principal
     * @return
     */
    SingleOperationRs editGoal(GoalRq payload, Principal principal) throws UserNotFoundException;

    /**
     * Удалить цель.
     *
     * @param principal
     * @param id
     * @return
     */
    AddCreditRs deleteGoal(Principal principal, Long id) throws UserNotFoundException;

    /**
     * Получить список ЗП по пользаку.
     * @param principal
     * @return
     */
    SalaryListRs getSalariesList(Principal principal) throws UserNotFoundException;

    /**
     * Редактировать зарплату.
     *
     * @param principal
     * @param payload
     * @return
     */
    SingleOperationRs editSalary(Principal principal, SalaryRq payload) throws UserNotFoundException;

    /**
     * Добавить новую ЗП.
     * @param principal
     * @param payload
     * @return
     */
    SingleOperationRs addSalary(Principal principal, SalaryRq payload);

    /**
     * Удалить ЗП.
     *
     * @param principal
     * @param id
     * @return
     */
    SingleOperationRs deleteSalary(Principal principal, Long id);

    /**
     * Добавить фриз.
     *
     * @param principal
     * @param request
     * @return
     */
    SingleOperationRs addFreeze(Principal principal, FreezeRq request);

    /**
     * Удалить фриз.
     *
     * @param principal
     * @param year
     * @param month
     * @return
     */
    SingleOperationRs deleteFreeze(Principal principal, Long year, Long month);

    /**
     * Получить свободные слоты по кредитам.
     *
     * @param principal
     * @param payload
     * @return
     */
    FreeLoanSlotsRs getLoansSlots(Principal principal, LoanByDateRq payload) throws UserNotFoundException;

    /**
     * Стартовать вычисления консолидированной таблицы.
     *
     * @param principal
     * @return
     */
    void startCalculation(Principal principal);

    /**
     * Получить статус потока
     */
    Integer getThreadStatus();
}
