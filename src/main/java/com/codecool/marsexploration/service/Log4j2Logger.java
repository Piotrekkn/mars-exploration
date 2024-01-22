package com.codecool.marsexploration.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class Log4j2Logger  {

    private static Log4j2Logger instance;




    public static Log4j2Logger getInstance(Class<?> clazz) {
        if (instance == null) {
            instance = new Log4j2Logger(clazz);
        }
        return instance;
    }

    private final Logger logger;
    public Log4j2Logger (Class<?> clazz) {
        this.logger = LogManager.getLogger();
    }
    public void logInfo(String message) {
        logger.info(message);
    }
    public void logError(String message) {
        logger.error(message);
    }
}
