package com.iamuv.moonshot.api.application;

import com.iamuv.moonshot.api.infrastructure.client.ApiGatewayClient;
import com.iamuv.moonshot.api.infrastructure.dto.*;
import com.iamuv.moonshot.api.domain.model.Api;
import com.iamuv.moonshot.api.domain.service.ApiService;
import com.iamuv.moonshot.api.domain.service.impl.DefaultApiServiceImpl;
import com.iamuv.moonshot.api.infrastructure.client.JsonConverter;
import com.iamuv.moonshot.api.infrastructure.client.LogClient;
import com.iamuv.moonshot.api.infrastructure.client.impl.DefaultLogClient;
import com.iamuv.moonshot.api.infrastructure.exception.MoonshotApiError;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class MoonshotApiApplication {

    private final String token;

    private final LogClient logClient;

    private final ApiService apiService;

    public MoonshotApiApplication(ApiGatewayClient apiGatewayClient, JsonConverter converter, String token, LogClient logClient) {
        this.token = token;
        this.logClient = logClient;
        apiService = new DefaultApiServiceImpl(converter, apiGatewayClient);
    }

    public MoonshotApiApplication(JsonConverter converter, String token, LogClient logClient) {
        this.token = token;
        this.logClient = logClient;
        apiService = new DefaultApiServiceImpl(converter);
    }

    public MoonshotApiApplication(ApiService apiService, String token, LogClient logClient) {
        this.token = token;
        this.logClient = logClient;
        this.apiService = apiService;
    }

    public MoonshotApiApplication(JsonConverter converter, String token) {
        this.token = token;
        this.logClient = new DefaultLogClient();
        apiService = new DefaultApiServiceImpl(converter);
    }

    public String getToken() {
        return token;
    }

    public LogClient getLogClient() {
        return logClient;
    }

    public ApiService getApiService() {
        return apiService;
    }

    public int estimateTokenCount(Api.ApiModel model,
                                  List<Api.Message> messages) throws MoonshotApiError, IOException, InterruptedException {
        try {
            return apiService.estimateTokenCount(token, model, messages);
        } catch (IOException | InterruptedException | MoonshotApiError e) {
            logClient.error("MoonShot Api Estimate Token Count error", e);
            throw e;
        }
    }

    public int estimateTokenCount(List<Api.Message> messages) throws MoonshotApiError, IOException, InterruptedException {
        return estimateTokenCount(Api.ApiModel.V1_8K, messages);
    }

    public int estimateTokenCount(Api.Message.MessageRole role, String message) throws MoonshotApiError, IOException, InterruptedException {
        List<Api.Message> messages = List.of(new Api.Message(message, role));
        return estimateTokenCount(messages);
    }

    public ChatCompletionDto chatCompletions(Api.ApiModel model, List<Api.Message> messages) throws MoonshotApiError, IOException, InterruptedException {
        try {
            return apiService.chatCompletions(token, messages, model);
        } catch (IOException | InterruptedException | MoonshotApiError e) {
            logClient.error("MoonShot Api Chat Completions error", e);
            throw e;
        }
    }

    public ChatCompletionDto chatCompletions(List<Api.Message> messages) throws MoonshotApiError, IOException, InterruptedException {
        return chatCompletions(Api.ApiModel.V1_8K, messages);
    }

    public ChatCompletionDto chatCompletions(Api.Message.MessageRole role, String message) throws MoonshotApiError, IOException, InterruptedException {
        List<Api.Message> messages = List.of(new Api.Message(message, role));
        return chatCompletions(messages);
    }

    public List<ModelDto> models() throws IOException, InterruptedException, MoonshotApiError {
        try {
            return apiService.models(token);
        } catch (IOException | InterruptedException | MoonshotApiError e) {
            logClient.error("MoonShot Api Get Models error", e);
            throw e;
        }
    }

    public FileDto uploadFile(Path filePath) throws IOException, InterruptedException, MoonshotApiError {
        try {
            return apiService.uploadFile(token, filePath);
        } catch (IOException | InterruptedException | MoonshotApiError e) {
            logClient.error("MoonShot Api Upload File error", e);
            throw e;
        }
    }

    public FileDto uploadFile(String filePath) throws IOException, InterruptedException, MoonshotApiError {
        return apiService.uploadFile(token, Paths.get(URI.create(filePath)));
    }

    public FileDto uploadFile(String path, String file) throws IOException, InterruptedException, MoonshotApiError {
        return apiService.uploadFile(token, Paths.get(path, file));
    }

    public FileDeleteDto deleteFile(String fileId) throws IOException, InterruptedException, MoonshotApiError {
        try {
            return apiService.deleteFile(token, fileId);
        } catch (IOException | InterruptedException | MoonshotApiError e) {
            logClient.error("MoonShot Api Upload File error", e);
            throw e;
        }
    }

    FileDto getFileInfo(String fileId) throws IOException, InterruptedException, MoonshotApiError {
        try {
            return apiService.getFileInfo(token, fileId);
        } catch (IOException | InterruptedException | MoonshotApiError e) {
            logClient.error("MoonShot Api Get File Info error", e);
            throw e;
        }
    }

    FileContentDto getFileContent(String fileId) throws IOException, InterruptedException, MoonshotApiError {
        try {
            return apiService.getFileContent(token, fileId);
        } catch (IOException | InterruptedException | MoonshotApiError e) {
            logClient.error("MoonShot Api Get File Content error", e);
            throw e;
        }
    }

    List<FileDto> getFileList() throws MoonshotApiError, IOException, InterruptedException {
        try {
            return apiService.getFileList(token);
        } catch (IOException | InterruptedException | MoonshotApiError e) {
            logClient.error("MoonShot Api Get File List error", e);
            throw e;
        }
    }

}
