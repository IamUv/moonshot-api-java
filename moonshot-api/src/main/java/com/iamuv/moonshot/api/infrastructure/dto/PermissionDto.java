package com.iamuv.moonshot.api.infrastructure.dto;

public class PermissionDto {

    private long created;

    private String id;

    private String object;

    private String organization;

    private String group;

    private boolean isBlocking;

    private boolean allowCreateEngine;

    private boolean allowFineTuning;

    private boolean allowLogprobs;

    private boolean allowSampling;

    private boolean allowSearchIndices;

    private boolean allowView;

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

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

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public boolean isBlocking() {
        return isBlocking;
    }

    public void setBlocking(boolean blocking) {
        isBlocking = blocking;
    }

    public boolean isAllowCreateEngine() {
        return allowCreateEngine;
    }

    public void setAllowCreateEngine(boolean allowCreateEngine) {
        this.allowCreateEngine = allowCreateEngine;
    }

    public boolean isAllowFineTuning() {
        return allowFineTuning;
    }

    public void setAllowFineTuning(boolean allowFineTuning) {
        this.allowFineTuning = allowFineTuning;
    }

    public boolean isAllowLogprobs() {
        return allowLogprobs;
    }

    public void setAllowLogprobs(boolean allowLogprobs) {
        this.allowLogprobs = allowLogprobs;
    }

    public boolean isAllowSampling() {
        return allowSampling;
    }

    public void setAllowSampling(boolean allowSampling) {
        this.allowSampling = allowSampling;
    }

    public boolean isAllowSearchIndices() {
        return allowSearchIndices;
    }

    public void setAllowSearchIndices(boolean allowSearchIndices) {
        this.allowSearchIndices = allowSearchIndices;
    }

    public boolean isAllowView() {
        return allowView;
    }

    public void setAllowView(boolean allowView) {
        this.allowView = allowView;
    }
}
