package com.example.gateway.dto;

public class CustomDto {
    private Boolean Logging;

    public CustomDto(Boolean logging) {
        Logging = logging;
    }

    public CustomDto() {
    }

    public Boolean getLogging() {
        return Logging;
    }

    public void setLogging(Boolean logging) {
        Logging = logging;
    }
}
