package com.leverx.dealerstat.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ExceptionInfo {

    @JsonProperty
    private String message;

    @JsonProperty
    private int code;

}
