package enums;

public enum DoctorCategoryStateEnum {
    SUCCESS(1,"创建成功"), INNER_ERROR(-1001,"操作失败"),EMPTY_LIST(-1002,"操作数少于1");
    private int state;
    private String stateInfo;
    private DoctorCategoryStateEnum(int state,String stateInfo){
        this.state=state;
        this.stateInfo=stateInfo;
    }
    public int getState(){
        return state;
    }
    public String getStateInfo(){
        return stateInfo;
    }
    public static DoctorCategoryStateEnum stateOf(int index) {
        for(DoctorCategoryStateEnum state:values()) {
            if(state.getState()==index) {
                return state;
            }
        }
        return null;
    }
}
