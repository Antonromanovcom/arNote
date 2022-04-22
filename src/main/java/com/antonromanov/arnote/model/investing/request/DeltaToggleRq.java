package com.antonromanov.arnote.model.investing.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class DeltaToggleRq {
    @NotBlank
    private String deltaType;
}
