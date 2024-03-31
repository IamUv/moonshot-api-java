package com.iamuv.moonshot.api.infrastructure.client;

import java.util.Collection;

public interface JsonConverter {

    String toJson(Object object);

    <T> T fromJson(String json, Class<T> clazz);

    <T> Collection<T> fromJsonToCollection(String json, Class<T> clazz);

    <T> T fromJson(String json, String key, Class<T> clazz);

    <T> Collection<T> fromJsonToCollection(String json, String key, Class<T> clazz);
}