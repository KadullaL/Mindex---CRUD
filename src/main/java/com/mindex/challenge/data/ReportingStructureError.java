package com.mindex.challenge.data;

public class ReportingStructureError {
    private String errorMessage;
    public ReportingStructureError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ReportingStructureError(){

    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
