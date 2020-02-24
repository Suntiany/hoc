package dto;

import entity.Hospital;
import enums.HospitalStateEnum;

import java.util.List;

public class HospitalExecution {
    //结果状态
    private int state;

    //状态标识
    private String stateInfo;

    //医院数量
    private int count;

    //操作的医院（增删改查时用）
    private Hospital hospital;

    //医院列表
    private List<Hospital> hospitalList;

    public HospitalExecution(){

    }

    //店铺操作失败的时候用的构造器
    public HospitalExecution(HospitalStateEnum stateEnum){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    //医院操作成功的时候使用的构造器
    public HospitalExecution(HospitalStateEnum stateEnum,Hospital hospital) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.hospital = hospital;
    }

    // 医院操作成功的时候使用的构造器
    public HospitalExecution(HospitalStateEnum stateEnum, List<Hospital> hospitalList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.hospitalList = hospitalList;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public List<Hospital> getHospitalList() {
        return hospitalList;
    }

    public void setHospitalList(List<Hospital> hospitalList) {
        this.hospitalList = hospitalList;
    }
}
