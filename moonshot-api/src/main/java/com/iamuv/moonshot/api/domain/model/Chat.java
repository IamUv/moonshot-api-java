package com.iamuv.moonshot.api.domain.model;

import java.util.List;
import java.util.Map;

public class Chat extends Api {

    protected static final String chatUrl = baseUrl + slash + version + slash + "chat";

    protected static final String completionsUrl = chatUrl + slash + "completions";

    public String getCompletionsUrl() {
        return completionsUrl;
    }

    public Map<String, String> getCompletionsRequestHeaders(String token) {
        return getDefaultJsonHeaders(token);
    }

    public Map<String, Object> getCompletionsRequestBody(List<Message> messages,
                                                     ApiModel model,
                                                     Integer maxTokens,
                                                     Float temperature,
                                                     Float topP,
                                                     Integer n,
                                                     Float presencePenalty,
                                                     Float frequencyPenalty,
                                                     List<String> stop,
                                                     boolean stream) {
        return
                new BodyBuilder()
                        .messages(messages)
                        .model(model)
                        .maxTokens(maxTokens)
                        .temperature(temperature)
                        .topP(topP).n(n)
                        .presencePenalty(presencePenalty)
                        .frequencyPenalty(frequencyPenalty)
                        .stop(stop).stream(stream).build();

    }


}
