package com.antonromanov.arnote.domain.investing.exceptions;

public class MoexXmlResponseMappingException extends RuntimeException {

    private String message;

    public MoexXmlResponseMappingException(String what) {
        this.message = "Данные с биржи успешно пришли, их маршелинг прошел, но мы не смогли достать из них: " + what;
    }

    public String getMessage() {
        return message;
    }
}
