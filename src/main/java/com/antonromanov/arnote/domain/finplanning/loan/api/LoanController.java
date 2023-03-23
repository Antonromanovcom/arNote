package com.antonromanov.arnote.domain.finplanning.loan.api;

import com.antonromanov.arnote.domain.finplanning.loan.dto.rq.CreditRq;
import com.antonromanov.arnote.domain.finplanning.loan.dto.rs.FreeLoanSlotsRs;
import com.antonromanov.arnote.domain.finplanning.loan.dto.rs.FullLoansListRs;
import com.antonromanov.arnote.domain.finplanning.loan.dto.rs.OperateCreditRs;
import com.antonromanov.arnote.domain.finplanning.loan.service.LoanService;
import com.antonromanov.arnote.domain.finplanning.loan.validation.ValidDate;
import com.antonromanov.arnote.domain.finplanning.loan.dto.rq.LoanByDateRq;
import com.antonromanov.arnote.domain.finplanning.loan.dto.rs.CreditRs;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
     */
    @CrossOrigin(origins = "*")
    @GetMapping()
    public FullLoansListRs creditList() {
        return service.getFullLoansList();
    }

    /**
     * Запросить свободные слоты по кредитам.
     *
     * @return
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/slots")
    public FreeLoanSlotsRs getLoansSlots(@RequestParam @ValidDate String date) {
        return service.getLoansSlots(date);
    }


    /**
     * Запросить кредит по дате.
     *
     * @return
     */
    @CrossOrigin(origins = "*")
    @PostMapping("/by-date")
    public FullLoansListRs loanByDate(@RequestBody LoanByDateRq payload) { // todo: вернуться позже
        return service.getLoanByDate(payload);
    }

    /**
     * Запросить данные по кредиту по его ID.
     *
     * @param id
     * @return
     */
    @CrossOrigin(origins = "*")
    @GetMapping("/get-by-id")
    public CreditRs getLoanById(@RequestParam @NotNull Long id) {
        return service.getLoanById(id);
    }

    /**
     * Редактировать кредит.
     *
     * @param payload
     * @return
     */
    @CrossOrigin(origins = "*")
    @PutMapping()
    public OperateCreditRs editLoan(@Valid @RequestBody CreditRq payload) {
        return service.editLoan(payload);
    }
}
