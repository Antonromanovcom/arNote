package com.antonromanov.arnote.domain.wish.dto.rq;

import com.antonromanov.arnote.sex.model.ArNoteUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

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

