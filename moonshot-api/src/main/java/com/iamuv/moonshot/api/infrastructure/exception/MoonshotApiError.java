package com.iamuv.moonshot.api.infrastructure.exception;

import com.iamuv.moonshot.api.infrastructure.util.JsonUtils;

import java.util.Locale;
import java.util.ResourceBundle;

public class MoonshotApiError extends Throwable {

    private static final String bundleBaseName = "moonshot_api_errors";

    private Locale locale; // 获取当前语言环境

    private final int httpStatusCode;

    private final String errorType;

    public MoonshotApiError(Locale locale, int httpStatusCode, String errorType, String message) {
        this.locale = locale == null ? Locale.getDefault() : locale;
        this.httpStatusCode = httpStatusCode;
        this.errorType = errorType;
    }

    public MoonshotApiError(int httpStatusCode, String errorType, String message) {
        this(Locale.getDefault(), httpStatusCode, errorType, message);
    }

    public MoonshotApiError(int httpStatusCode, String errorType) {
        this(Locale.getDefault(), httpStatusCode, errorType, null);
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getErrorType() {
        return errorType;
    }

    @Override
    public String getMessage() {
        if (getErrorType() != null) {
            ResourceBundle bundle = ResourceBundle.getBundle(bundleBaseName, locale);
            String msg = bundle.getString(getErrorType());
            if (!msg.isBlank()) {
                return msg;
            }
        }
        return super.getMessage();
    }

    /**
     * {"code":3,"error":"EOF","message":"非法输入","scode":"0x3","status":false}
     * {"error":{"message":"auth failed","type":"invalid_authentication_error"}}
     */
    public static class Builder {

        private Locale locale;

        private int httpStatusCode = 500;

        private String errorType;

        private String message;

        public Builder json(String json) {
            this.message = JsonUtils.getStringValueFromJson(json, "message");
            this.errorType = JsonUtils.getStringValueFromJson(json, "type");
            if (this.errorType == null) {
                this.errorType = JsonUtils.getStringValueFromJson(json, "error");
            }
            return this;
        }

        public Builder httpStatusCode(int httpStatusCode) {
            this.httpStatusCode = httpStatusCode;
            return this;
        }

        public Builder local(Locale locale) {
            this.locale = locale;
            return this;
        }

        public MoonshotApiError build() {
            return new MoonshotApiError(locale, httpStatusCode, errorType, message);
        }

    }

}
