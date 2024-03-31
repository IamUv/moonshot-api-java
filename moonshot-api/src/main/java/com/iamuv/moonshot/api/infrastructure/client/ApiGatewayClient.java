package com.iamuv.moonshot.api.infrastructure.client;

import com.iamuv.moonshot.api.infrastructure.dto.ApiGatewayDto;

import java.io.IOException;
import java.util.Map;

public interface ApiGatewayClient {

    ApiGatewayDto get(Map<String, String> headers, String url) throws IOException, InterruptedException;

    ApiGatewayDto post(Map<String, String> headers, String url, String body) throws IOException, InterruptedException;

    ApiGatewayDto file(Map<String, String> headers, String url, String content) throws IOException, InterruptedException;

    ApiGatewayDto delete(Map<String, String> headers, String url) throws IOException, InterruptedException;
}
