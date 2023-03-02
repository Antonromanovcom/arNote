package com.antonromanov.arnote.domain.finplanning.freeze.api;

import com.antonromanov.arnote.domain.finplanning.common.dto.rs.SingleOperationRs;
import com.antonromanov.arnote.domain.finplanning.freeze.service.FreezeService;
import com.antonromanov.arnote.domain.finplanning.freeze.dto.rq.FreezeRq;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotNull;

/**
 * API фризов. Фриз - это когда сальдо (итог) для месяца не расчитывается по формуле, а пользак задает его сам руками.
 */
@CrossOrigin()
@RestController
@RequestMapping("/freeze")
@AllArgsConstructor
public class FreezeController {

    private final FreezeService service;


    /**
     * Добавить фриз.
     *
     * @param request
     * @return
     */
    @CrossOrigin(origins = "*")
    @PostMapping()
    public SingleOperationRs addFreeze(@RequestBody FreezeRq request) {
        return service.addFreeze( request);
    }

    /**
     * Удалить фриз.
     *
     * @param
     * @return
     */
    @CrossOrigin(origins = "*")
    @DeleteMapping()
    public SingleOperationRs deleteFreeze(@RequestParam @NotNull Integer year, @RequestParam @NotNull Integer month) {
        return service.deleteFreeze(year, month);
    }
}
