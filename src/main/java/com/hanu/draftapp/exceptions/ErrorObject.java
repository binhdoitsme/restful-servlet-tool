package com.hanu.draftapp.exceptions;

public final class ErrorObject {
    private final String error;

    public ErrorObject(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
