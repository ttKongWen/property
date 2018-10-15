package com.ilovecl.demo._const;

/**
 * 催单状态的常量
 *
 */

public enum UrgentRepairEnum {
    CHECK_WAIT(0, "待管理员查看"),
    CHECK(1, "管理员已查看"),
    CANCELED_BY_STUDENT(2, "被学生取消");

    private int state;
    private String stateInfo;

    UrgentRepairEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static UrgentRepairEnum stateOf(int index) {
        for (UrgentRepairEnum state : values()) {
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
