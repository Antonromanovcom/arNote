package com.antonromanov.arnote.model.temp;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SalePointItem {

    @JsonUnwrapped
    private AdditionalInfo accountInfo;
    public SalePointInfo salePointInfo;
}
