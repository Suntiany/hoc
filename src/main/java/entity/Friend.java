package entity;

public class Friend {
    private int friendId;
    private Long userId;
    private int hospitalId;
    private String userFollow;
    private String hospitalFollow;
    private User user;
    private Hospital hospital;

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

    public int getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(int hospitalId) {
        this.hospitalId = hospitalId;
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
