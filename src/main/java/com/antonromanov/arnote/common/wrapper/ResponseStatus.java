package com.antonromanov.arnote.common.wrapper;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseStatus {

    private Boolean success;
    private String description;
    private String errorCode;

}
