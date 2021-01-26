package com.antonromanov.arnote.model.investing.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AddInstrumentRq {
    private String ticker;
    private boolean isPlan; // если не передана ни одна продажа - то это "план", иначе это факт.
}
