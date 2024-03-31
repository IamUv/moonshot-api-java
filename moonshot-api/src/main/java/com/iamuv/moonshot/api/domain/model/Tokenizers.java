package com.iamuv.moonshot.api.domain.model;

import java.util.List;
import java.util.Map;

public class Tokenizers extends Api {

    protected static final String tokenizersUrl = baseUrl + slash + version + slash + "tokenizers";

    protected static final String estimateTokenCountUrl = tokenizersUrl + slash + "estimate-token-count";


    public Map<String, Object> getEstimateTokenCountRequestBody(ApiModel model, List<Message> messages) {
        return new BodyBuilder().messages(messages).model(model).build();

    }

    public Map<String, String> getEstimateTokenCountRequestHeaders(String token) {
        return getDefaultJsonHeaders(token);
    }


    public String getEstimateTokenCountUrl() {
        System.out.println(estimateTokenCountUrl);
        return estimateTokenCountUrl;
    }

}
