package dao;

import BaseTest.BaseTest;
import com.fasterxml.jackson.databind.ser.Serializers;
import entity.Friend;
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
        friend.setHospitalId(27);
        friend.setUserFollow("已提交");
        friend.setHospitalFollow("待审核");
        int effectedNum = friendDao.insertFriend(friend);
        assertEquals(1,effectedNum);
    }

    @Test
    public void testUpdateFriend(){
        Friend friend = new Friend();
        friend.setHospitalId(25);
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
        friend.setHospitalId(25);
        int effectedNum = friendDao.delete(friend);
        assertEquals(1,effectedNum);
    }

    @Test
    public void testSelectByHospitalId(){
        Integer hospitalId  = 27;
        List<Friend> friendList = friendDao.selectByHospitalId(hospitalId);
        System.out.println(friendList.size());
        System.out.println(friendList.get(0).getHospital().getHospitalName());
    }

    @Test
    public void testSelectByUserId(){
        Integer userId = 1;
        List<Friend> friendList = friendDao.selectByUserId(userId);
        System.out.println(friendList.size());
        System.out.println(friendList.get(0).getHospital().getHospitalName());
    }
}
