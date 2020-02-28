package enums;

public enum DoctorStateEnum {
    OFFLINE(-1, "非法医生账号"), DOWN(0, "下架"), SUCCESS(1, "操作成功"), INNER_ERROR(-1001, "操作失败"), EMPTY(-1002, "医生信息为空");

    private int state;

    private String stateInfo;

    private DoctorStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static DoctorStateEnum stateOf(int index) {
        for (DoctorStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
