package com.antonromanov.arnote.common.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalWrapper implements WrapperModel {

    Object body;
    ResponseStatus status;

    @Override
    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    @Override
    public void setBody(Object object) {
        this.body = object;
    }


}
