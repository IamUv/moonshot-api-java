package com.iamuv.moonshot.api.infrastructure.client.impl;

import com.iamuv.moonshot.api.infrastructure.client.ApiGatewayClient;
import com.iamuv.moonshot.api.infrastructure.dto.ApiGatewayDto;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public class DefaultApiGatewayClient implements ApiGatewayClient {

    HttpClient client;

    public DefaultApiGatewayClient(HttpClient client) {
        this.client = client;
    }

    public DefaultApiGatewayClient(Duration connectTimeout) {
        this(HttpClient.newBuilder().connectTimeout(connectTimeout).build());
    }

    public DefaultApiGatewayClient() {
        this(Duration.of(5, ChronoUnit.MINUTES));
    }

    @Override
    public ApiGatewayDto get(Map<String, String> headers, String url) throws IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        headers.forEach(requestBuilder::header);
        requestBuilder.uri(URI.create(url));
        HttpRequest request = requestBuilder.GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return new ApiGatewayDto(response.statusCode(), response.body());
    }

    @Override
    public ApiGatewayDto post(Map<String, String> headers, String url, String body) throws IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        headers.forEach(requestBuilder::header);
        requestBuilder.uri(URI.create(url));
        HttpRequest request = requestBuilder.POST(HttpRequest.BodyPublishers.ofString(body)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return new ApiGatewayDto(response.statusCode(), response.body());
    }

    @Override
    public ApiGatewayDto file(Map<String, String> headers, String url, String content) throws IOException, InterruptedException {
        return post(headers, url, content);
    }

    @Override
    public ApiGatewayDto delete(Map<String, String> headers, String url) throws IOException, InterruptedException {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        headers.forEach(requestBuilder::header);
        requestBuilder.uri(URI.create(url));
        HttpRequest request = requestBuilder.DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return new ApiGatewayDto(response.statusCode(), response.body());
    }
}
