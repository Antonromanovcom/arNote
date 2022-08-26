package com.antonromanov.arnote;

import lombok.Builder;
import lombok.Setter;

@Builder
public class GlobalResponse<T> implements GlobalResponseWrapper<T>{

    private T body;

    @Setter
    private ResponseStatus status;


    @Override
    public ResponseStatus getStatus() {
        return this.status;
    }

    @Override
    public T getBody() {
        return this.body;
    }

}
