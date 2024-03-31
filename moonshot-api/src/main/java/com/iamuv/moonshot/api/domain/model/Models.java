package com.iamuv.moonshot.api.domain.model;

import java.util.Map;

public class Models extends Api {
    protected static final String modelsUrl = baseUrl + slash + version + slash + "models";

    public String getUrl() {
        return modelsUrl;
    }


    public Map<String, String> getModelsRequestHeaders(String token) {
        return getDefaultJsonHeaders(token);
    }

}
