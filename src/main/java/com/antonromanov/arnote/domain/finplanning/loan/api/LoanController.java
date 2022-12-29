package com.antonromanov.arnote.domain.finplanning.loan.api;

import com.antonromanov.arnote.domain.finplanning.loan.dto.rs.OperateCreditRs;
import com.antonromanov.arnote.domain.finplanning.loan.dto.rq.CreditRq;
import com.antonromanov.arnote.domain.finplanning.loan.service.LoanService;
import com.antonromanov.arnote.domain.finplanning.loan.dto.rs.FullLoansListRs;
import com.antonromanov.arnote.domain.finplanning.loan.validation.ValidDate;
import com.antonromanov.arnote.domain.finplanning.loan.dto.rs.FreeLoanSlotsRs;
import com.antonromanov.arnote.sex.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * API для кредитов [ФИН-ПЛАНИРОВАНИЕ].
 */
@CrossOrigin()
@RestController
@RequestMapping("/fin-planning/loan")
@AllArgsConstructor
@Validated
public class LoanController {

    private final LoanService service;

    /**
     * Добавить кредит.
     *
     * @param request
     * @return
     */
    @CrossOrigin(origins = "*")
    @PostMapping()
    public OperateCreditRs addLoan(@Valid @RequestBody CreditRq request) {
        return service.addLoan(request);
    }

    /**
     * Удалить кредит.
     *
     * @param id - id кредита
     * @return
     */
    @CrossOrigin(origins = "*")
    @DeleteMapping()
    public OperateCreditRs deleteLoan(@RequestParam @NotNull Long id) {
        return service.deleteLoan(id);
    }

    /**
     * Запросить полный список кредитов.
     *
     * @return
     * @throws Exception
     */
    @CrossOrigin(origins = "*")
    @GetMapping()
    public FullLoansListRs creditList() throws UserNotFoundException {
        return service.getFullLoansList();
    }

    /**
     * Запросить свободные слоты по кредитам.
     *
     * @return
     * @throws Exception
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/slots")
    public FreeLoanSlotsRs getLoansSlots(@RequestParam @ValidDate String date) throws UserNotFoundException {
        return service.getLoansSlots(date);
    }


    /**
     * Запросить кредит по дате.
     *
     * @param principal
     * @return
     * @throws Exception
     */
   /* @CrossOrigin(origins = "*")
    @PostMapping("/loan/bydate")
    public FullLoansListRs loanByDate(Principal principal, @RequestBody LoanByDateRq payload) {
        log.info("Credit by Date = {}", payload);
        return service.getLoanByDate(payload, principal);
    }*/

    /**
     * Запросить данные по кредиту по его ID.
     *
     * @param principal
     * @param id
     * @return
     * @throws UserNotFoundException
     */
  /*  @CrossOrigin(origins = "*")
    @GetMapping("/loan")
    public CreditRs getLoanById(Principal principal, @RequestParam @NotNull Long id) throws UserNotFoundException { // todo: надо отдельную ДТО с респонсом или как-то научиться заворачивать их (враппить)
        log.info("Get loan by id: {}", id);
        return service.getLoanById(id, principal);
    }*/

    /**
     * Редактировать кредит.
     *
     * @param principal
     * @param payload
     * @return
     * @throws UserNotFoundException
     */
  /*  @CrossOrigin(origins = "*")
    @PutMapping("/loan")
    public AddCreditRs editLoan(Principal principal, @RequestBody CreditRq payload) throws UserNotFoundException {
        log.info("Edit loan: {}", payload);
        return service.editLoan(payload, principal);
    }*/
}
