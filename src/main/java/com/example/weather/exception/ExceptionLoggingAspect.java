package com.example.weather.exception;

import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionLoggingAspect.class);

    @AfterThrowing(value = "@annotation(LogException)", throwing = "exception")
    public void logException(Exception exception) {
        logger.error("Exception occurred: ", exception);
    }
}
