package dao;

import BaseTest.BaseTest;
import com.fasterxml.jackson.databind.ser.Serializers;
import entity.Friend;
import entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class FriendDaoTest extends BaseTest {
 @Autowired
 private FriendDao friendDao;

    @Test
    public void testInsertFriend(){
        Friend friend = new Friend();
        friend.setUserId(2L);
        friend.setHospitalId(27L);
        friend.setUserFollow("已提交");
        friend.setHospitalFollow("待审核");
        int effectedNum = friendDao.insertFriend(friend);
        assertEquals(1,effectedNum);
    }

    @Test
    public void testUpdateFriend(){
        Friend friend = new Friend();
        friend.setHospitalId(25L);
        friend.setUserId(1L);
        friend.setUserFollow("已提交");
        friend.setHospitalFollow("审核成功");
        int effectedNum = friendDao.updateFriend(friend);
        assertEquals(1,effectedNum);
    }

    @Test
    public void testDeleteFriend(){
        Friend friend = new Friend();
        friend.setUserId(1L);
        friend.setHospitalId(25L);
        int effectedNum = friendDao.delete(friend);
        assertEquals(1,effectedNum);
    }

    @Test
    public void testSelectByHospitalId(){
        Long hospitalId  = 17L;
        List<Friend> friendList = friendDao.selectByHospitalId(hospitalId);
        System.out.println(friendList.size());
        System.out.println(friendList.get(0).getUser().getEmail());
    }

    @Test
    public void testSelectByUserId(){
        List<Friend> friendList = friendDao.selectByUserId(3L);
        System.out.println(friendList.size());
        System.out.println(friendList.get(0).getUser().getAddr());
    }

    @Test
    public void testSelectByUserIdAndHospitalId(){
        Long userId = 3L;
        Long hospitalId = 17L;
        Friend friend = friendDao.selectByUserIdAndHospitalId(3L,17L);
        System.out.println(friend.getUser().getUserId());
        System.out.println(friend.getHospital().getHospitalId());
    }
}
