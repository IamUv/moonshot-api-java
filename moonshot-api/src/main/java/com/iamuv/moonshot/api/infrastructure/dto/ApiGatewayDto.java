package com.iamuv.moonshot.api.infrastructure.dto;

public class ApiGatewayDto {

    private int statusCode;

    private String body;

    public int statusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String body() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public ApiGatewayDto(int statusCode, String body) {
        this.statusCode = statusCode;
        this.body = body;
    }

    public ApiGatewayDto() {
    }
}
