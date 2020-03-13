package service;

import dto.FriendExecution;
import entity.Friend;

import java.util.List;

public interface FriendService {
    FriendExecution insertFriendShip(Friend friend);
    FriendExecution updateFriendShip(Friend friend);
    FriendExecution deleteFriendShip(Friend friend);
    FriendExecution getByHospitalId(int hospitalId);
    FriendExecution getByUserId(int UserId);
}
