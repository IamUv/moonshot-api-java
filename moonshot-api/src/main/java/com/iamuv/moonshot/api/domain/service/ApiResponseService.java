package com.iamuv.moonshot.api.domain.service;

import com.iamuv.moonshot.api.infrastructure.dto.ApiGatewayDto;
import com.iamuv.moonshot.api.infrastructure.exception.MoonshotApiError;

import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.stream.Stream;

public interface ApiResponseService {

    <T> T getResultFromResponseByField(ApiGatewayDto response, Class<T> clazz) throws MoonshotApiError;

    <T> T getResultFromResponseByField(ApiGatewayDto response, Class<T> clazz, String fieldName) throws MoonshotApiError;

    <T> Collection<T> getResultsFromResponseByField(ApiGatewayDto response, Class<T> clazz, String fieldName) throws MoonshotApiError;

    <T> Collection<T> getStreamResultFromResponse(HttpResponse<Stream<String>> response, Class<T> clazz) throws MoonshotApiError;


}
