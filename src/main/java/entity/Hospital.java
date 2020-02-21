package entity;

import java.util.Date;

public class Hospital {
    private Long HospitalId;
    private String ownerName;
    private String hospitalName;
    private String hospitalDesc;
    private String hospitalAddr;
    private String phone;
    private String hospitalImg;
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;
    //-1.不可用 0.审核中 1.可用
    private Integer enableStatus;
    private Area area;

    public Long getHospitalId() {
        return HospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        HospitalId = hospitalId;
    }

    public String getOwnerId() {
        return ownerName;
    }

    public void setOwnerId(String ownerId) {
        this.ownerName = ownerId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalDesc() {
        return hospitalDesc;
    }

    public void setHospitalDesc(String hospitalDesc) {
        this.hospitalDesc = hospitalDesc;
    }

    public String getHospitalAddr() {
        return hospitalAddr;
    }

    public void setHospitalAddr(String hospitalAddr) {
        this.hospitalAddr = hospitalAddr;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHospitalImg() {
        return hospitalImg;
    }

    public void setHospitalImg(String hospitalImg) {
        this.hospitalImg = hospitalImg;
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

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
