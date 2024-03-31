package com.iamuv.moonshot.api.domain.service.impl;

import com.iamuv.moonshot.api.infrastructure.dto.ApiGatewayDto;
import com.iamuv.moonshot.api.domain.service.ApiResponseService;
import com.iamuv.moonshot.api.infrastructure.client.JsonConverter;
import com.iamuv.moonshot.api.infrastructure.exception.MoonshotApiError;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class DefaultApiResponseServiceImpl implements ApiResponseService {

    private static final String dataPrefix = "data:";

    private static final String done = "[DONE]";

    private final JsonConverter jsonConverter;

    public DefaultApiResponseServiceImpl(JsonConverter jsonConverter) {
        this.jsonConverter = jsonConverter;
    }

    @Override
    public <T> T getResultFromResponseByField(ApiGatewayDto response, Class<T> clazz) throws MoonshotApiError {
        if (response.statusCode() == 200) {
            return jsonConverter.fromJson(response.body(), clazz);
        }
        throw new MoonshotApiError.Builder()
                .json(response.body())
                .httpStatusCode(response.statusCode())
                .build();
    }

    @Override
    public <T> T getResultFromResponseByField(ApiGatewayDto response, Class<T> clazz, String fieldName) throws MoonshotApiError {
        if (response.statusCode() == 200) {
            return jsonConverter.fromJson(response.body(), fieldName, clazz);
        }
        throw new MoonshotApiError.Builder()
                .json(response.body())
                .httpStatusCode(response.statusCode())
                .build();
    }

    @Override
    public <T> Collection<T> getResultsFromResponseByField(ApiGatewayDto response, Class<T> clazz, String fieldName) throws MoonshotApiError {
        if (response.statusCode() == 200) {
            return jsonConverter.fromJsonToCollection(response.body(), fieldName, clazz);
        }
        throw new MoonshotApiError.Builder()
                .json(response.body())
                .httpStatusCode(response.statusCode())
                .build();
    }

    @Override
    public <T> Collection<T> getStreamResultFromResponse(HttpResponse<Stream<String>> response, Class<T> clazz) throws MoonshotApiError {
        if (response.statusCode() == 200) {
            List<T> results = new ArrayList<>();
            response.body().forEach(s -> {
                s = getDataJsonFromStreamString(s);
                if (!s.equalsIgnoreCase(done)) {
                    results.add(jsonConverter.fromJson(s, clazz));
                }
            });
            return results;
        }
        throw new MoonshotApiError.Builder()
                .json(response.body().toList().get(0))
                .httpStatusCode(response.statusCode())
                .build();
    }


    private String getDataJsonFromStreamString(String stream) {
        if (stream.startsWith(dataPrefix)) {
            return stream.substring(dataPrefix.length()).trim();
        }
        return stream.trim();
    }

}
