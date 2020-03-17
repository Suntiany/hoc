package service;

import dto.FriendExecution;
import entity.Friend;

import java.util.List;

public interface FriendService {
    FriendExecution insertFriendShip(Friend friend);
    FriendExecution updateFriendShip(Friend friend);
    FriendExecution deleteFriendShip(Friend friend);
    FriendExecution getByHospitalId(long hospitalId);
    FriendExecution getByUserId(long UserId);
    FriendExecution getByUserIdAndHospitalId(long userId,long hospitalId);

    /**
     * 查询审核通过的用户列表，用以指派家庭医生
     * @return
     */
    FriendExecution getFriendByHospitalId(long hospitalId);
}
