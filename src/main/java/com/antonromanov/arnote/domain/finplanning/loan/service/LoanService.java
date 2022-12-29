package com.antonromanov.arnote.domain.finplanning.loan.service;

import com.antonromanov.arnote.domain.finplanning.loan.dto.rq.CreditRq;
import com.antonromanov.arnote.domain.finplanning.loan.dto.rs.OperateCreditRs;
import com.antonromanov.arnote.domain.finplanning.loan.dto.rs.FullLoansListRs;
import com.antonromanov.arnote.domain.finplanning.loan.dto.rs.FreeLoanSlotsRs;
import com.antonromanov.arnote.old.exceptions.BadIncomeParameter;
import com.antonromanov.arnote.old.exceptions.UserNotFoundException;

public interface LoanService {


    /**
     * Добавить кредит.
     *
     * @param request
     * @return
     */
    OperateCreditRs addLoan(CreditRq request) throws UserNotFoundException, BadIncomeParameter;

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
     * Редактировать кредит.
     *
     * @param payload
     * @param principal
     * @return
     */
  //  AddCreditRs editLoan(CreditRq payload, Principal principal) throws UserNotFoundException;

    /**
     * Добавить доход
     *
     * @param payload
     * @param principal
     * @return
     */
   // SingleOperationRs addIncome(IncomeRq payload, Principal principal) throws UserNotFoundException;

    /**
     * Удалить доход
     *
     * @param principal - юзер
     * @return
     */
  //  SingleOperationRs deleteIncome(Principal principal, IncomesForDeleteRq req) throws UserNotFoundException;

    /**
     * Редактировать доход.
     *
     * @param payload
     * @param principal
     * @return
     */
  //  SingleOperationRs editIncome(IncomeRq payload, Principal principal) throws UserNotFoundException;

    /**
     * Получить данные по кредиту по ID.
     *
     * @param id
     * @param principal
     * @return
     */
  //  CreditRs getLoanById(Long id, Principal principal) throws UserNotFoundException;

    /**
     * Деталка по остаткам.
     *
     * @param payload
     * @param principal
     * @return
     */
   /* FinalBalanceCalculationsRs getRemainsDetailInfo(GetRemainsDetailInfoRq payload, Principal principal) throws UserNotFoundException;

    SingleOperationRs addGoal(GoalRq payload, Principal principal) throws UserNotFoundException;

    FullLoansListRs getLoanByDate(LoanByDateRq payload, Principal principal);*/

    /**
     * Редактировать цель.
     *
     * @param payload
     * @param principal
     * @return
     */
  //  SingleOperationRs editGoal(GoalRq payload, Principal principal) throws UserNotFoundException;

    /**
     * Удалить цель.
     *
     * @param principal
     * @param id
     * @return
     */
 //   AddCreditRs deleteGoal(Principal principal, Long id) throws UserNotFoundException;

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


}
