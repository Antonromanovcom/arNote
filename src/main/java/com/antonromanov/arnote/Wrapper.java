package com.antonromanov.arnote;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wrapper implements IWrapperModel{
    @JsonUnwrapped
    Object main;

    String someInfo;

    @Override
    public void setData(Object object) {
        someInfo = object.toString();
    }

    @Override
    public void setBody(Object object) {
        main = object;
    }
}
