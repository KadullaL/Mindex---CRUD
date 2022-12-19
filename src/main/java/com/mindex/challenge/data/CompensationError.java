package com.mindex.challenge.data;

public class CompensationError {
    private String errorMessage;
    public CompensationError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public CompensationError(){

    }
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
