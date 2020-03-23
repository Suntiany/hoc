package entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class DoctorAuth {
    private Long localDoctorId;
    private String username;
    private String password;
    private Date CreateTime;
    private Date lastEditTime;
    private Doctor doctor;

    public Long getLocalDoctorId() {
        return localDoctorId;
    }

    public void setLocalDoctorId(Long localDoctorId) {
        this.localDoctorId = localDoctorId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Date createTime) {
        CreateTime = createTime;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
