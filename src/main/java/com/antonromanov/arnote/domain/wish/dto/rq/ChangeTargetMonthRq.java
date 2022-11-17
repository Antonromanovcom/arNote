package com.antonromanov.arnote.domain.wish.dto.rq;

import com.antonromanov.arnote.domain.wish.enums.TargetMonthStepType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeTargetMonthRq {

    @NotNull
    private  Long id;
    @NotNull
    private TargetMonthStepType type;
}

