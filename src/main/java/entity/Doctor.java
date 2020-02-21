package entity;

import java.util.Date;
import java.util.List;

public class Doctor {
    private Long doctorId;
    private String doctorName;
    private String doctorDesc;
    //简略图
    private String imgAddr;
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;
    //-1.不可用 0.下架 1.在前端展示系统展示
    private Integer enableStatus;
    private List<DoctorImg> doctorImgList;
    private DoctorCategory doctorCategory;
    private Hospital hospital;

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorDesc() {
        return doctorDesc;
    }

    public void setDoctorDesc(String doctorDesc) {
        this.doctorDesc = doctorDesc;
    }

    public String getImgAddr() {
        return imgAddr;
    }

    public void setImgAddr(String imgAddr) {
        this.imgAddr = imgAddr;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }

    public List<DoctorImg> getDoctorImgList() {
        return doctorImgList;
    }

    public void setDoctorImgList(List<DoctorImg> doctorImgList) {
        this.doctorImgList = doctorImgList;
    }

    public DoctorCategory getDoctorCategory() {
        return doctorCategory;
    }

    public void setDoctorCategory(DoctorCategory doctorCategory) {
        this.doctorCategory = doctorCategory;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }
}
