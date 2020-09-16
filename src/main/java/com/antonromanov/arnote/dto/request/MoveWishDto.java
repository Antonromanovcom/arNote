package com.antonromanov.arnote.dto.request;

import com.antonromanov.arnote.enums.StepType;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO для перемещения желаний.
 */
@Data
public class MoveWishDto {
    private long id;
    private String month; //todo: может тоже енум (можно сделать двуязычный)???
    private StepType step;
}
