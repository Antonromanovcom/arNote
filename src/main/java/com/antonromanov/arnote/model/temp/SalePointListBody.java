package com.antonromanov.arnote.model.temp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SalePointListBody {
    StatusRs status;
    Boolean resultsLastPage;
    String cacheStatus;
    List<SalePointItem> listOfSalePoint;
}
