package com.ilovecl.demo.dto;

import java.sql.Timestamp;

/**
 * 学生调用查看催单后的返回结果
 *
 */
public class StudentUrgentResult {
    private int id; // 催单编号
    private int status; // 催单状态
    private String statusInfo; // 催单状态的字符串
    private int repairId; // 催单对应的报修单编号
    private String repairDetail; // 催单对应的报修单详情
    private String studentId; // 提交催单的学生的编号
    private Timestamp createTime; // 催单创建的时间

    public StudentUrgentResult(int id, int status, String statusInfo, int repairId, String repairDetail, String studentId, Timestamp createTime) {
        this.id = id;
        this.status = status;
        this.statusInfo = statusInfo;
        this.repairId = repairId;
        this.repairDetail = repairDetail;
        this.studentId = studentId;
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }

    public int getRepairId() {
        return repairId;
    }

    public void setRepairId(int repairId) {
        this.repairId = repairId;
    }

    public String getRepairDetail() {
        return repairDetail;
    }

    public void setRepairDetail(String repairDetail) {
        this.repairDetail = repairDetail;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
