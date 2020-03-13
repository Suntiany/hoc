package dto;

import entity.Hospital;
import entity.User;
import enums.UserStateEnum;

import java.util.List;

public class UserExecution {
    //结果状态
    private int state;
    //状态标识
    private String stateInfo;
    //用户数量
    private int count;
    //操作的医院（增删改查时使用）
    private User user;
    private List<User> userList;

    public UserExecution(){}

    //用户操作失败时使用的构造器
    public UserExecution(UserStateEnum stateEnum){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    //用户操作成功的时候使用的构造器
    public UserExecution(UserStateEnum stateEnum, User user) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.user = user;
    }

    //用户操作成功的时候使用的构造器
    public UserExecution(UserStateEnum stateEnum,List<User> userList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.userList = userList;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
