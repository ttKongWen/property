package com.ilovecl.demo.dto;

/**
 * 登录接口的返回封装数据
 *
 */
public class LoginResult {
    //    是否登录成功
    private boolean isSuccess;
    private String reason;

    public LoginResult(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public LoginResult(boolean isSuccess, String reason) {
        this.isSuccess = isSuccess;
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    @Override
    public String toString() {
        return "登录结果：" + String.valueOf(isSuccess);
    }

    //    Session数据

}
