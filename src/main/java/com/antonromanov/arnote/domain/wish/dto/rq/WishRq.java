package com.antonromanov.arnote.domain.wish.dto.rq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishRq {

    private  Long id;

    @NotBlank
    private  String wishName;
    @NotNull
    private  Integer price;
    @NotNull
    private  Integer priority;
    private  String description;
    private  String url;
    private  Boolean archive;
    private  Boolean realized;
}

