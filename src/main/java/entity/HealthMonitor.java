package entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class HealthMonitor {
    private int monitorId;
    private User user;
    private Date createTime;
    private Date lastEditTime;
    private float bloodGlucose;
    private float bloodPressureLow;
    private float bloodPressureHigh;
    private float heartRate;

    public int getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(int monitorId) {
        this.monitorId = monitorId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public float getBloodGlucose() {
        return bloodGlucose;
    }

    public void setBloodGlucose(float bloodGlucose) {
        this.bloodGlucose = bloodGlucose;
    }

    public float getBloodPressureLow() {
        return bloodPressureLow;
    }

    public void setBloodPressureLow(float bloodPressureLow) {
        this.bloodPressureLow = bloodPressureLow;
    }

    public float getBloodPressureHigh() {
        return bloodPressureHigh;
    }

    public void setBloodPressureHigh(float bloodPressureHigh) {
        this.bloodPressureHigh = bloodPressureHigh;
    }

    public float getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(float heartRate) {
        this.heartRate = heartRate;
    }
}
