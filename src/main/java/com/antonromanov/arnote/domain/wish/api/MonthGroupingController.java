package com.antonromanov.arnote.domain.wish.api;

import com.antonromanov.arnote.domain.wish.dto.rq.ChangeTargetMonthRq;
import com.antonromanov.arnote.domain.wish.dto.rq.WishTransferRq;
import com.antonromanov.arnote.domain.wish.dto.rs.GroupedMonthListRs;
import com.antonromanov.arnote.domain.wish.dto.rs.WishRs;
import com.antonromanov.arnote.domain.wish.service.WishService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;

/**
 * REST-контроллер для Желаний.
 */
@CrossOrigin()
@RestController
@RequestMapping("/wishes/grouping")
@Slf4j
@AllArgsConstructor
public class MonthGroupingController {

    private final WishService wishService;


    /**
     * Получить все желания с группировкой по месяцам.
     *
     * @param principal
     * @return
     */
    @CrossOrigin(origins = "*")
    @GetMapping()
    public GroupedMonthListRs getAllWishesWithMonthGrouping(Principal principal, @RequestParam(required = false) String sortType) {
        return wishService.getAllWishesWithMonthGrouping(principal, sortType);
    }

    /**
     * Изменить у желание месяц в каком планируется его реализация.
     *
     * @param request
     * @return
     */
    @CrossOrigin(origins = "*")
    @PutMapping("/move/transfer")
    public WishRs changeMonthOrder(@Valid @RequestBody WishTransferRq request) {
        return wishService.transferWish(request);
    }

    /**
     * +/- 1 месяц к реализации желания.
     *
     * @param payload
     * @return
     */
    @CrossOrigin(origins = "*")
    @PutMapping("/move/one-step-change")
    public WishRs oneStepChangeTargetMonth(@Valid @RequestBody ChangeTargetMonthRq payload) {
        return wishService.oneStepChangeTargetMonth(payload);
    }
}
