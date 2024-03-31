package com.iamuv.moonshot.api.springboot.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.iamuv.moonshot.api.infrastructure.client.JsonConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JacksonJsonConverter implements JsonConverter {

    private final ObjectMapper objectMapper;

    public JacksonJsonConverter() {
        this.objectMapper = new ObjectMapper();
        // 配置PropertyNamingStrategy为SNAKE_CASE
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        // 确保在序列化时使用下划线命名
        objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
    }

    @Override
    public String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> Collection<T> fromJsonToCollection(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<T>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T fromJson(String json, String key, Class<T> clazz) {
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode personNode = rootNode.path(key);
            return objectMapper.treeToValue(personNode, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> Collection<T> fromJsonToCollection(String json, String key, Class<T> clazz) {
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode keyNode = rootNode.path(key);
            List<T> results = new ArrayList<>();
            for (JsonNode n : keyNode) {
                results.add(objectMapper.treeToValue(n, clazz));
            }
            return results;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
