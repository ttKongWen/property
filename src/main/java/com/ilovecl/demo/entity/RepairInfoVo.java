package com.ilovecl.demo.entity;

/**
 * 报修视图信息
 */
public class RepairInfoVo {

    private Repair repair;
    private Student student;
    private String statesInfo;

    public RepairInfoVo(Repair repair){
        this.repair = repair;
    }

    public RepairInfoVo(Student student, Repair repair){
        this.student= student;
        this.repair = repair;
    }

    public Repair getRepair() {
        return repair;
    }

    public void setRepair(Repair repair) {
        this.repair = repair;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getStatesInfo() {
        return statesInfo;
    }

    public void setStatesInfo(String stateInfo) {
        this.statesInfo = stateInfo;
    }
}
