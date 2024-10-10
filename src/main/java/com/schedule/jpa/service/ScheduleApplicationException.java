package com.schedule.jpa.service;

import com.schedule.jpa.controller.exception.ErrorCodes;
import org.springframework.http.HttpStatus;

public class ScheduleApplicationException extends RuntimeException {

    private final ErrorCodes errorCodes;

    public ScheduleApplicationException(ErrorCodes errorCodes) {
        this.errorCodes = errorCodes;
    }

    public ErrorCodes getErrorCodes() {
        return errorCodes;
    }

    public HttpStatus getHttpStatus() {
        return errorCodes.status;
    }
}
