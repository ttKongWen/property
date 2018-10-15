package com.ilovecl.demo.dto;

/**
 * 注册的返回结果
 *
 */
public class RegisterResult {
    // 注册是否成功
    private boolean isSuccess;

    // 原因，注册失败时生效
    private String reason;

    public RegisterResult(boolean isSuccess, String reason) {
        this.isSuccess = isSuccess;
        this.reason = reason;
    }

    public RegisterResult(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return String.valueOf(isSuccess) + " : " + reason;
    }
}
