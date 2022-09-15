package com.antonromanov.arnote.domain.wish.dto.rq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishRq {

    @NotBlank
    private  String wishName;
    @NotNull
    private  Integer price;
    @NotNull
    private  Integer priority;
    private  String description;
    private  String url;
}

