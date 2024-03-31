package com.iamuv.moonshot.api.domain.service.impl;

import com.iamuv.moonshot.api.domain.model.*;
import com.iamuv.moonshot.api.domain.service.ApiResponseService;
import com.iamuv.moonshot.api.infrastructure.client.ApiGatewayClient;
import com.iamuv.moonshot.api.infrastructure.client.JsonConverter;
import com.iamuv.moonshot.api.infrastructure.client.impl.DefaultApiGatewayClient;
import com.iamuv.moonshot.api.infrastructure.dto.*;
import com.iamuv.moonshot.api.infrastructure.exception.MoonshotApiError;
import com.iamuv.moonshot.api.infrastructure.util.BoundaryGenerator;
import com.iamuv.moonshot.api.domain.service.ApiService;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DefaultApiServiceImpl implements ApiService {

    private final JsonConverter jsonConverter;

    private final ApiGatewayClient httpClient;

    private final ApiResponseService apiResponseService;

    public DefaultApiServiceImpl(JsonConverter jsonConverter, ApiGatewayClient httpClient, ApiResponseService apiResponseService) {
        this.jsonConverter = jsonConverter;
        this.httpClient = httpClient;
        this.apiResponseService = apiResponseService;
    }

    public DefaultApiServiceImpl(JsonConverter jsonConverter, ApiGatewayClient httpClient) {
        this(jsonConverter, httpClient, new DefaultApiResponseServiceImpl(jsonConverter));
    }

    public DefaultApiServiceImpl(JsonConverter jsonConverter) {
        this(jsonConverter, new DefaultApiGatewayClient(), new DefaultApiResponseServiceImpl(jsonConverter));
    }

    @Override
    public int estimateTokenCount(String token, Api.ApiModel model, List<Api.Message> messages) throws IOException, InterruptedException, MoonshotApiError {
        Tokenizers tokenizers = new Tokenizers();
        String body = jsonConverter.toJson(tokenizers.getEstimateTokenCountRequestBody(model, messages));
        ApiGatewayDto response = httpClient.post(tokenizers.getEstimateTokenCountRequestHeaders(token), tokenizers.getEstimateTokenCountUrl(), body);
        return apiResponseService.getResultFromResponseByField(response, EstimateTokenCountDto.class).getData().getTotalTokens();
    }

    @Override
    public ChatCompletionDto chatCompletions(String token, List<Api.Message> messages, Api.ApiModel model) throws IOException, InterruptedException, MoonshotApiError {
        return chatCompletions(token, messages, model, null, null, null, null, null, null, null);
    }

    @Override
    public ChatCompletionDto chatCompletions(String token, List<Api.Message> messages, Api.ApiModel model, Integer maxTokens, Float temperature, Float topP, Integer n, Float presencePenalty, Float frequencyPenalty, List<String> stop) throws IOException, InterruptedException, MoonshotApiError {
        Chat chat = new Chat();
        String body = jsonConverter.toJson(chat.getCompletionsRequestBody(messages, model, maxTokens, temperature, topP, n, presencePenalty, frequencyPenalty, stop, false));
        ApiGatewayDto response = httpClient.post(chat.getCompletionsRequestHeaders(token), chat.getCompletionsUrl(), body);
        return apiResponseService.getResultFromResponseByField(response, ChatCompletionDto.class);
    }

    @Override
    public List<ModelDto> models(String token) throws IOException, InterruptedException, MoonshotApiError {
        Models models = new Models();
        ApiGatewayDto response = httpClient.get(models.getModelsRequestHeaders(token), models.getUrl());
        return new ArrayList<>(apiResponseService.getResultsFromResponseByField(response, ModelDto.class, "data"));
    }

    @Override
    public FileDto uploadFile(String token, Path filePath) throws IOException, InterruptedException, MoonshotApiError {
        Files files = new Files();
        String boundary = BoundaryGenerator.generateBoundary();
        ApiGatewayDto response = httpClient.file(files.getUploadHeaders(token, boundary), files.getFilesUrl(), files.getUploadRequestBody(filePath, boundary));
        return apiResponseService.getResultFromResponseByField(response, FileDto.class);
    }

    @Override
    public FileDeleteDto deleteFile(String token, String id) throws IOException, InterruptedException, MoonshotApiError {
        Files files = new Files();
        ApiGatewayDto response = httpClient.delete(files.getDefaultHeaders(token), files.getFilesDeleteUrl(id));
        return apiResponseService.getResultFromResponseByField(response, FileDeleteDto.class);
    }

    @Override
    public FileDto getFileInfo(String token, String id) throws IOException, InterruptedException, MoonshotApiError {
        Files files = new Files();
        ApiGatewayDto response = httpClient.get(files.getDefaultHeaders(token), files.getFilesGetUrl(id));
        return apiResponseService.getResultFromResponseByField(response, FileDto.class);
    }

    @Override
    public FileContentDto getFileContent(String token, String id) throws IOException, InterruptedException, MoonshotApiError {
        Files files = new Files();
        ApiGatewayDto response = httpClient.get(files.getDefaultHeaders(token), files.getFilesContentUrl(id));
        return apiResponseService.getResultFromResponseByField(response, FileContentDto.class);
    }

    @Override
    public List<FileDto> getFileList(String token) throws MoonshotApiError, IOException, InterruptedException {
        Files files = new Files();
        ApiGatewayDto response = httpClient.get(files.getDefaultHeaders(token), files.getFilesUrl());
        return new ArrayList<>(apiResponseService.getResultsFromResponseByField(response, FileDto.class, "data"));
    }


}
