package com.antonromanov.arnote.domain.investing.dto.response.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class DoubleSerializer extends JsonSerializer<Double> {


    @Override
    public void serialize(Double value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        DecimalFormat formatter = new DecimalFormat();
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);
        formatter.setGroupingUsed(false);
        DecimalFormatSymbols sym = DecimalFormatSymbols.getInstance();
        sym.setDecimalSeparator('.');
        formatter.setDecimalFormatSymbols(sym);
        jsonGenerator.writeNumber(formatter.format(value));
    }
}
