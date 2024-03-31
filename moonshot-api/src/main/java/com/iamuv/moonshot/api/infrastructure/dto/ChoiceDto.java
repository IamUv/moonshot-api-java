package com.iamuv.moonshot.api.infrastructure.dto;

public class ChoiceDto {

    private int index;

    private String finishReason;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getFinishReason() {
        return finishReason;
    }

    public void setFinishReason(String finishReason) {
        this.finishReason = finishReason;
    }
}
