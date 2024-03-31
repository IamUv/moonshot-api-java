package com.iamuv.moonshot.api.infrastructure.client.impl;

import com.iamuv.moonshot.api.infrastructure.client.LogClient;

import java.util.logging.Level;

public class DefaultLogClient implements LogClient {

    private final java.util.logging.Logger logger;

    public DefaultLogClient(String name) {
        logger = java.util.logging.Logger.getLogger(name);
    }

    public DefaultLogClient() {
        this("moonshot api application");
    }

    @Override
    public void debug(String message) {
        logger.fine(message);
    }

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void warn(String message, Throwable e) {
        logger.log(Level.WARNING, message, e);
    }

    @Override
    public void error(String message, Throwable e) {
        logger.log(Level.SEVERE, message, e);
    }

    @Override
    public void warn(String message) {
        logger.warning(message);
    }

    @Override
    public void error(String message) {
        logger.severe(message);
    }
}
