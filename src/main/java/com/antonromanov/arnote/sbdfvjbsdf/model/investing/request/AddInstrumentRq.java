package com.antonromanov.arnote.model.investing.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AddInstrumentRq {
    @NotBlank
    private String ticker;
    private boolean isPlan; // если не передана ни одна продажа - то это "план", иначе это факт.
    private Double price; // цена покупки
    private LocalDate purchaseDate; // дата покупки
    private int lot; // количество акций
    @NotNull
    private String bondType;
}
