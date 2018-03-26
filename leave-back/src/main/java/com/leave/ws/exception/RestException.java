package com.leave.ws.exception;

import com.leave.utils.CustomStatus;
import org.springframework.http.HttpStatus;

public class RestException extends Exception {
    /**
     * SERIAL ID
     */
    private static final long serialVersionUID = 1L;
    private HttpStatus status;
    private String message;
    private String devStatus;

    public RestException(String message, HttpStatus status, CustomStatus customStatus) {
        super();
        this.message = message;
        this.devStatus = customStatus != null ? customStatus.getStatus() : null;
        this.status = status != null ? status : HttpStatus.OK;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public RestError geRestError() {
        return new RestError(message, devStatus);
    }

    public static class RestError {
        private String message;
        private String devStatus;

        public RestError(String message, String devStatus) {
            super();
            this.message = message;
            this.devStatus = devStatus;
        }

        public String getMessage() {
            return message;
        }

        public String getDevStatus() {
            return devStatus;
        }
    }
}