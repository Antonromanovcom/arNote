package com.antonromanov.arnote.domain.wish.dto.rq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishTransferRq {

    @NotNull
    private  Long id;
    @NotBlank
    private  String monthAndYear;
}

