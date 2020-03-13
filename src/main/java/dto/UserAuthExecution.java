package dto;

import entity.UserAuth;
import enums.UserAuthStateEnum;

import java.util.List;

public class UserAuthExecution {
    private int state;
    private String stateInfo;
    private int count;
    private UserAuth userAuth;
    private List<UserAuth> userAuthList;
    public UserAuthExecution(){}
    //失败的构造器
    public UserAuthExecution(UserAuthStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }
    //成功的构造器
    public UserAuthExecution(UserAuthStateEnum stateEnum,UserAuth userAuth) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.userAuth = userAuth;
    }
    //成功的构造器
    public UserAuthExecution(UserAuthStateEnum stateEnum,List<UserAuth> userAuthList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.userAuthList = userAuthList;
    }
}
