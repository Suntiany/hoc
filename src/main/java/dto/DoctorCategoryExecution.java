package dto;

import entity.DoctorCategory;
import enums.DoctorCategoryStateEnum;

import java.util.List;

public class DoctorCategoryExecution {
    //结果状态
    private int state;
    //状态标识
    private String stateInfo;
    private List<DoctorCategory> doctorCategoryList;
    public DoctorCategoryExecution(){}
    //操作成功的时候使用的构造器
    public DoctorCategoryExecution(DoctorCategoryStateEnum stateEnum,List<DoctorCategory> doctorCategoryList){
        this.state=stateEnum.getState();
        this.stateInfo=stateEnum.getStateInfo();
        this.doctorCategoryList=doctorCategoryList;
    }
    //操作失败的时候使用的构造器
    public DoctorCategoryExecution(DoctorCategoryStateEnum stateEnum){
        this.state=stateEnum.getState();
        this.stateInfo=stateEnum.getStateInfo();
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public List<DoctorCategory> getDoctorCategoryList() {
        return doctorCategoryList;
    }

    public void setDoctorCategoryList(List<DoctorCategory> doctorCategoryList) {
        this.doctorCategoryList = doctorCategoryList;
    }
}
