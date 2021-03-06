package service.impl;

import dao.FriendDao;
import dto.FriendExecution;
import entity.Friend;
import enums.FriendStateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.FriendService;

import java.util.List;

@Service
public class FriendServiceImpl implements FriendService {
    @Autowired
    private FriendDao friendDao;

    @Override
    public FriendExecution insertFriendShip(Friend friend) {
        //判断friend形参里面的userId和hospitalId是否为空
        if(friend.getHospitalId()>-1&&friend.getUserId()>-1){
            friend.setUserFollow("已提交");
            friend.setHospitalFollow("待审核");
            try{
                int effectedNum = friendDao.insertFriend(friend);
                if(effectedNum<=0){
                    throw new RuntimeException("提交失败");
                }
                return new FriendExecution(FriendStateEnum.SUCCESS,friend);
            }catch (Exception e){
                throw new RuntimeException("创建签约失败" + e.toString());
            }
        }else{
            return new FriendExecution(FriendStateEnum.EMPTY);
        }
    }

    @Override
    public FriendExecution updateFriendShip(Friend friend) {
        //判断friend形参里面的userId和hospitalId是否为空
        if(friend.getHospitalId()>-1&&friend.getUserId()>-1){
            friend.setUserFollow("已提交");
            friend.setHospitalFollow("已审核");
            try{
                int effectedNum = friendDao.updateFriend(friend);
                if(effectedNum<=0){
                    throw new RuntimeException("审核失败");
                }
                return new FriendExecution(FriendStateEnum.SUCCESS,friend);
            }catch (Exception e){
                throw new RuntimeException("审核失败" + e.toString());
            }
        }else{
            return new FriendExecution(FriendStateEnum.EMPTY);
        }
    }

    @Override
    public FriendExecution deleteFriendShip(Friend friend) {
        //判断friend形参里面的userId和hospitalId是否为空
        if(friend.getHospitalId()>-1&&friend.getUserId()>-1){
            try{
                int effectedNum = friendDao.delete(friend);
                if(effectedNum<=0){
                    throw new RuntimeException("解约失败");
                }
                return new FriendExecution(FriendStateEnum.SUCCESS);
            }catch (Exception e){
                throw new RuntimeException("解约失败" + e.toString());
            }
        }else{
            return new FriendExecution(FriendStateEnum.EMPTY);
        }
    }

    @Override
    public FriendExecution getByHospitalId(long hospitalId) {
        List<Friend> friendList = friendDao.selectByHospitalId(hospitalId);
        return new FriendExecution(FriendStateEnum.SUCCESS,friendList);
    }

    @Override
    public FriendExecution getByUserId(long UserId) {
        List<Friend> friendList = friendDao.selectByUserId(UserId);
        return new FriendExecution(FriendStateEnum.SUCCESS,friendList);
    }

    @Override
    public FriendExecution getByUserIdAndHospitalId(long userId, long hospitalId) {
        Friend friend =  friendDao.selectByUserIdAndHospitalId(userId,hospitalId);
        return new FriendExecution(FriendStateEnum.SUCCESS,friend);
    }

    @Override
    public FriendExecution getFriendByHospitalId(long hospitalId) {
        try{
            List<Friend> friendList = friendDao.selectFriendByHospitalId(hospitalId);
            if(friendList.size()>0){
                return new FriendExecution(FriendStateEnum.SUCCESS,friendList);
            }
        }catch (Exception e){
            return new FriendExecution(FriendStateEnum.INNER_ERROR);
        }

        return null;
    }
}
