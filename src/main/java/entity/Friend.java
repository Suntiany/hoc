package entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Friend {
    private int friendId;
    private Long userId;
    private String userFollow;
    private String hospitalFollow;
    private User user;
    private Hospital hospital;
    private Long hospitalId;
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public String getUserFollow() {
        return userFollow;
    }

    public void setUserFollow(String userFollow) {
        this.userFollow = userFollow;
    }

    public String getHospitalFollow() {
        return hospitalFollow;
    }

    public void setHospitalFollow(String hospitalFollow) {
        this.hospitalFollow = hospitalFollow;
    }
}
