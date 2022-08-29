package com.antonromanov.arnote;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
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

    @Override
    public void setBody(T body) {
        this.body = body;
    }
}
