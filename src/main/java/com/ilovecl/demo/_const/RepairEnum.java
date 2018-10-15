package com.ilovecl.demo._const;

/**
 * 报修单状态的常量
 *
 */
public enum RepairEnum {

    DELETED_BY_STUDENT(0, "DELETED_BY_STUDENT_CN"),
    REPAIR_UN_ARRANGED(1, "REPAIR_UN_ARRANGED"),
    REPAIR_ARRANGED(2, "REPAIR_ARRANGED"),
    CANCELED_AGREE_WAIT(3, "CANCELED_AGREE_WAIT"),
    CANCELED_AGREE(4, "CANCELED_AGREE"),
    CANCELED_REJECT(5, "CANCELED_REJECT"),
    CONFIRM_WAIT(6, "CONFIRM_WAIT"),
    CONFIRM(7, "CONFIRM");

    private int state;
    private String stateInfo;

    RepairEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static RepairEnum stateOf(int index) {
        for (RepairEnum state : values()) {
            if ((state.getState() == index)) {
                return state;
            }
        }

        return null;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }


}
