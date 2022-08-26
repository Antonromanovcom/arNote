package com.antonromanov.arnote;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseStatus {

    private Boolean success;
    private String description;

}
