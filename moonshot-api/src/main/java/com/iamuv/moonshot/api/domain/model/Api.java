package com.iamuv.moonshot.api.domain.model;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Api {

    protected static String baseUrl = "https://api.moonshot.cn";

    protected static String version = "v1";

    protected static char slash = '/';

    protected static String bearerPrefix = "Bearer ";

    protected static String authorization = "Authorization";

    protected static String contentType = "Content-Type";

    protected static String contentTypeApplicationJson = "application/json";

    protected static String contentTypeMultipartFormData = "multipart/form-data";

    protected String buildAuthorizationHeader(String token) {
        return bearerPrefix + token;
    }

    public enum ApiModel {

        V1_8K("moonshot-v1-8k"),

        V1_32K("moonshot-v1-32k"),

        V1_128K("moonshot-v1-128k");

        private final String model;

        ApiModel(String model) {
            this.model = model;
        }

        @Override
        public String toString() {
            return model();
        }

        public String model() {
            return model;
        }
    }


    public static class Message {

        protected String content;

        protected MessageRole role;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public MessageRole getRole() {
            return role;
        }

        public void setRole(MessageRole role) {
            this.role = role;
        }

        public Message(String content, MessageRole role) {
            this.content = content;
            this.role = role;
        }

        public Message() {
        }

        public enum MessageRole {

            USER("user"),
            SYSTEM("system"),

            ASSISTANT("assistant ");

            private final String role;

            MessageRole(String role) {
                this.role = role;
            }

            @Override
            public String toString() {
                return role();
            }

            public String role() {
                return this.role;
            }
        }

    }

    protected Map<String, String> getDefaultJsonHeaders(String token) {
        return Map.of(contentType, contentTypeApplicationJson, authorization, buildAuthorizationHeader(token));
    }

    public static class BodyBuilder {

        private final Map<String, Object> body;

        public BodyBuilder() {
            this.body = new ConcurrentHashMap<>();
        }

        public BodyBuilder model(ApiModel model) {
            body.put("model", model.toString());
            return this;
        }

        public BodyBuilder messages(List<Message> messages) {
            body.put("messages", messages);
            return this;
        }

        public BodyBuilder temperature(Float temperature) {
            if (temperature != null)
                body.put("temperature", temperature);
            return this;
        }

        public BodyBuilder maxTokens(Integer maxTokens) {
            if (maxTokens != null)
                body.put("max_tokens", maxTokens);
            return this;
        }

        public BodyBuilder topP(Float topP) {
            if (topP != null)
                body.put("top_p", topP);
            return this;
        }

        public BodyBuilder n(Integer n) {
            if (n != null)
                body.put("n", n);
            return this;
        }

        public BodyBuilder presencePenalty(Float presencePenalty) {
            if (presencePenalty != null)
                body.put("presence_penalty", presencePenalty);
            return this;
        }

        public BodyBuilder frequencyPenalty(Float frequencyPenalty) {
            if (frequencyPenalty != null)
                body.put("frequency_penalty", frequencyPenalty);
            return this;
        }

        public BodyBuilder stop(String stop) {
            if (stop != null)
                body.put("stop", stop);
            return this;
        }

        public BodyBuilder stop(List<String> stops) {
            if (stops != null && !stops.isEmpty())
                body.put("stop", stops);
            return this;
        }

        public BodyBuilder stream(boolean stream) {
            if (stream)
                body.put("stream", true);
            return this;
        }

        public Map<String, Object> build() {
            return body;
        }

    }

}
