package com.ilovecl.demo.dto;

/**
 * 修改保修单的结果
 *
 */
public class ModifyRepairResult {
    // 是否成功
    boolean isSuccess;

    // 原因，仅在失败时有效
    String reason;

    public ModifyRepairResult(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public ModifyRepairResult(boolean isSuccess, String reason) {
        this.isSuccess = isSuccess;
        this.reason = reason;
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
}
