package entity;

import java.util.Date;

public class AreaAuth {
    private Long localAreaId;
    private String username;
    private String password;
    private Date CreateTime;
    private Date lastEditTime;
    private Area area;

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Long getLocalAreaId() {
        return localAreaId;
    }

    public void setLocalAreaId(Long localAreaId) {
        this.localAreaId = localAreaId;
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
}
