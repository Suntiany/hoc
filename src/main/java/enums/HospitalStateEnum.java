package enums;

import entity.Hospital;

public enum HospitalStateEnum {
    CHECK(0, "审核中"), OFFLINE(-1, "非法店铺"), SUCCESS(1, "操作成功"), PASS(2, "通过认证"), INNER_ERROR(-1001,
            "内部系统错误"), NULL_HOSPITALID(-1002, "HospitalId为空"),NULL_HOSPITAL(-1003, "Hospital信息为空");
    private int state;
    private String stateInfo;

    private HospitalStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    /**
     * 依据传入的state返回相应的enum值
     */
    public static HospitalStateEnum stateOf(int state) {
        for (HospitalStateEnum stateEnum : values()) {
            if (stateEnum.getState() == state) {
                return stateEnum;
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
