package com.leave.ws.exception;

import org.springframework.http.HttpStatus;

public class RestException extends Exception {
    /**
     * SERIAL ID
     */
    private static final long serialVersionUID = 1L;
    private HttpStatus status;
    private String message;

    public RestException(String message, HttpStatus status) {
        super();
        this.message = message;
        this.status = status != null ? status : HttpStatus.OK;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public RestError geRestError() {
        return new RestError(message);
    }

    public static class RestError {
        private String message;

        public RestError(String message) {
            super();
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}