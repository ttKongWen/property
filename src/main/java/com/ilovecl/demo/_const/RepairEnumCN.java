package com.ilovecl.demo._const;

/**
 * 报修单的状态，中文版
 *
 */
public enum RepairEnumCN {
    DELETED_BY_STUDENT_CN(0, "被学生删除"),
    REPAIR_UN_ARRANGED_CN(1, "未安排检修"),
    REPAIR_ARRANGED_CN(2, "已安排检修"),
    CANCELED_AGREE_WAIT_CN(3, "待学生确认取消"),
    CANCELED_AGREE_CN(4, "学生同意取消"),
    CANCELED_REJECT_CN(5, "学生拒绝取消"),
    CONFIRM_WAIT_CN(6, "等待验收"),
    CONFIRM_CN(7, "已验收");

    private int state;
    private String stateInfo;

    RepairEnumCN(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static RepairEnumCN stateOf(int index) {
        for (RepairEnumCN state : values()) {
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
