package com.iamuv.moonshot.api.infrastructure.client;

public interface LogClient {

    void debug(String message);

    void info(String message);

    void warn(String message, Throwable e);

    void error(String message, Throwable e);

    void warn(String message);

    void error(String message);
}
