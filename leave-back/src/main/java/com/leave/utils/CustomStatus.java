package com.leave.utils;

public enum CustomStatus {
    ACCOUNT_EXIST("#400-1", "Account already exist"), ERROR_LOGIN("#400-2", "Error Login");

    private final String status;
    private final String reasonPhrase;

    public String getStatus() {
        return status;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    CustomStatus(String status, String reasonPhrase) {
        this.status = status;
        this.reasonPhrase = reasonPhrase;
    }
}