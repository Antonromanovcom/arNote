package com.antonromanov.arnote.domain.wish.api;

import com.antonromanov.arnote.domain.wish.dto.WishAnalyticsRs;
import com.antonromanov.arnote.domain.wish.dto.rq.ChangePriorityRq;
import com.antonromanov.arnote.domain.wish.dto.rq.SearchWishRq;
import com.antonromanov.arnote.domain.wish.dto.rq.WishRq;
import com.antonromanov.arnote.domain.wish.dto.rs.WishListRs;
import com.antonromanov.arnote.domain.wish.dto.rs.WishRs;
import com.antonromanov.arnote.domain.wish.service.WishService;
import com.antonromanov.arnote.old.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * REST-контроллер для Желаний.
 */
@CrossOrigin()
@RestController
@RequestMapping("/wishes")
@AllArgsConstructor
public class WishController {

    private final WishService wishService;

    /**
     * Получить все желания.
     *
     * @param filter - тип фильтрации: все или только приоритетные
     * @param sort   - собственно сортировка
     * @return
     */
    @CrossOrigin(origins = "*")
    @GetMapping
    public WishListRs getAllWishes(@RequestParam(required = false) String filter, @RequestParam(required = false) String sort) {
        return wishService.getAllWishesByUserId(filter, sort);
    }

    /**
     * Поиск желаний.
     *
     * @return
     */
    @CrossOrigin(origins = "*")
    @PostMapping("/find")
    public WishListRs findWishes(@Valid @RequestBody SearchWishRq request) throws UserNotFoundException {
        return wishService.findWishesByName(request.getWishName());
    }

    @CrossOrigin(origins = "*")
    @PostMapping
    public WishRs addWish(@Valid @RequestBody WishRq request) {
        return wishService.addWish(request);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping()
    public WishRs deleteWish(@RequestParam String id) {
        return wishService.deleteWish(id);
    }

    @CrossOrigin(origins = "*")
    @PutMapping
    public WishRs updateWish(@Valid @RequestBody WishRq request) {
        return wishService.updateWish(request);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/analytics")
    public WishAnalyticsRs getAnalytics() {
        return wishService.getWishAnalytics();
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/change-priority")
    public WishRs changePriority(@Valid @RequestBody ChangePriorityRq payload) {
        return wishService.oneStepChangePriority(payload);
    }
}
