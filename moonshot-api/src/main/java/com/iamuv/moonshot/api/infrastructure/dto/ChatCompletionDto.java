package com.iamuv.moonshot.api.infrastructure.dto;

import java.util.List;

public class ChatCompletionDto {

    private String id;

    private String object;

    private long created;

    private String model;

    private List<ChoiceDto> choices;

    private UsageDto usage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<ChoiceDto> getChoices() {
        return choices;
    }

    public void setChoices(List<ChoiceDto> choices) {
        this.choices = choices;
    }

    public UsageDto getUsage() {
        return usage;
    }

    public void setUsage(UsageDto usage) {
        this.usage = usage;
    }
}
