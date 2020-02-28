package dto;

import entity.Doctor;
import enums.DoctorStateEnum;

import java.util.List;

public class DoctorExecution {
    // 结果状态
    private int state;

    // 状态标识
    private String stateInfo;

    //医生数量
    private int count;

    //操作的医生
    private Doctor doctor;

    //获取Doctor 列表
    private List<Doctor> doctorList;

    public DoctorExecution() {

    }

    //失败的构造器
    public DoctorExecution(DoctorStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    //成功的构造器
    public DoctorExecution(DoctorStateEnum stateEnum,Doctor doctor) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.doctor = doctor;
    }

    //成功的构造器
    public DoctorExecution(DoctorStateEnum stateEnum,List<Doctor> doctorList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.doctorList = doctorList;
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

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public List<Doctor> getDoctorList() {
        return doctorList;
    }

    public void setDoctorList(List<Doctor> doctorList) {
        this.doctorList = doctorList;
    }
}
