package com.ilovecl.demo.dto;

import java.sql.Timestamp;

/**
 * 维修记录的返回结果
 *
 */
public class MaintenanceResult {
    private int id; // 维修记录编号

    private int repairId; // 维修记录对应的报修单的编号
    private String repairDetail; // 维修记录对应的报修单的详情

    private int technicianId; // 维修人员的编号
    private String technicianName; // 维修人员的名字

    private Timestamp startTime; // 维修记录发起的时间

    public MaintenanceResult(int id, int repairId, String repairDetail, int technicianId, String technicianName, Timestamp startTime) {
        this.id = id;
        this.repairId = repairId;
        this.repairDetail = repairDetail;
        this.technicianId = technicianId;
        this.technicianName = technicianName;
        this.startTime = startTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(int technicianId) {
        this.technicianId = technicianId;
    }

    public String getTechnicianName() {
        return technicianName;
    }

    public void setTechnicianName(String technicianName) {
        this.technicianName = technicianName;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }
}
