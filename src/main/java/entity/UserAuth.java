package entity;

import java.util.Date;

public class UserAuth {
    private Long localUserId;
    private String username;
    private String password;
    private Date CreateTime;
    private Date lastEditTime;
    private User user;

    public Long getLocalUserId() {
        return localUserId;
    }

    public void setLocalUserId(Long localUserId) {
        this.localUserId = localUserId;
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

    public Date getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Date createTime) {
        CreateTime = createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
