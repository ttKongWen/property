package com.ilovecl.demo.entity;

import java.sql.Timestamp;

/**
 * 维修记录
 *
 */
public class Maintenance {
    private int id; // 维修记录编号
    private int repairId; // 维修记录对应的报修单的编号
    private int technicianId; // 维修人员的编号
    private Timestamp startTime; // 维修记录发起的时间

    public Maintenance(int id) {
        this.id = id;
    }

    public Maintenance(Integer id, Integer repairId, Integer technicianId, Timestamp startTime) {
        this.id = id;
        this.repairId = repairId;
        this.technicianId = technicianId;
        this.startTime = startTime;
    }

    public Maintenance(Integer repairId, Integer technicianId, Timestamp startTime) {
        this.repairId = repairId;
        this.technicianId = technicianId;
        this.startTime = startTime;
    }

    public int getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(int technicianId) {
        this.technicianId = technicianId;
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

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }
}
