package com.antonromanov.arnote;

public interface GlobalResponseWrapper<T> {
    ResponseStatus getStatus();
    T getBody();
    void setBody(T body);
}
