package entity;

import java.util.Date;

public class DoctorCategory {
    private Long doctorCategoryId;
    private String doctorCategoryName;
    private int priority;
    private Date createTime;
    private Long hospitalId;

    public Long getDoctorCategoryId() {
        return doctorCategoryId;
    }

    public void setDoctorCategoryId(Long doctorCategoryId) {
        this.doctorCategoryId = doctorCategoryId;
    }

    public String getDoctorCategoryName() {
        return doctorCategoryName;
    }

    public void setDoctorCategoryName(String doctorCategoryName) {
        this.doctorCategoryName = doctorCategoryName;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }
}
