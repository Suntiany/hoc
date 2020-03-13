package dto;

import entity.Doctor;
import entity.Friend;
import enums.DoctorStateEnum;
import enums.FriendStateEnum;

import java.util.List;

public class FriendExecution {
    // 结果状态
    private int state;

    // 状态标识
    private String stateInfo;


    private int count;


    private Friend friend;


    private List<Friend> friendList ;

    public FriendExecution() {

    }

    //失败的构造器
    public FriendExecution(FriendStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    //成功的构造器
    public FriendExecution(FriendStateEnum stateEnum,Friend friend) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.friend = friend;
    }

    //成功的构造器
    public FriendExecution(FriendStateEnum stateEnum,List<Friend> friendList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.friendList = friendList;
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

    public Friend getFriend() {
        return friend;
    }

    public void setFriend(Friend friend) {
        this.friend = friend;
    }

    public List<Friend> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<Friend> friendList) {
        this.friendList = friendList;
    }
}
