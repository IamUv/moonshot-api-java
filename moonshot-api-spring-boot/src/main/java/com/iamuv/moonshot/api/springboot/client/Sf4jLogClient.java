package com.iamuv.moonshot.api.springboot.client;

import com.iamuv.moonshot.api.infrastructure.client.LogClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sf4jLogClient implements LogClient {

    protected Logger log;

    public Sf4jLogClient(String logName) {
        log = LoggerFactory.getLogger(logName);
    }

    public Sf4jLogClient() {
        this("moonshot api app");
    }

    @Override
    public void debug(String message) {
        log.debug(message);
    }

    @Override
    public void info(String message) {
        log.info(message);
    }

    @Override
    public void warn(String message, Throwable e) {
        log.warn(message, e);
    }

    @Override
    public void error(String message, Throwable e) {
        log.error(message, e);
    }

    @Override
    public void warn(String message) {
        log.warn(message);
    }

    @Override
    public void error(String message) {
        log.error(message);
    }
}
