package com.antonromanov.arnote.domain.wish.dto.rq;

import com.antonromanov.arnote.domain.wish.enums.StepType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePriorityRq {

    @NotNull
    private  Long id;
    @NotNull
    private StepType type;
}

