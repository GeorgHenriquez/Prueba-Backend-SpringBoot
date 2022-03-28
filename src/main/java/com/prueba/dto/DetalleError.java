package com.prueba.dto;

import java.util.List;

public class DetalleError {

    private int code;
    private boolean success;
    private String message;
    private List<String> errorData;

    public DetalleError(int code, boolean success, String message, List<String> errorData) {
        super();
        this.code = code;
        this.success = success;
        this.message = message;
        this.errorData = errorData;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrorData() {
        return errorData;
    }

    public void setErrorData(List<String> errorData) {
        this.errorData = errorData;
    }

}
