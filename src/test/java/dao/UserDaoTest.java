package dao;

import BaseTest.BaseTest;
import entity.Area;
import entity.User;
import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserDaoTest extends BaseTest {
    @Autowired
    private UserDao userDao;

    @Test
    public void testInsertUser(){
        User user = new User();
        Area area = new Area();
        area.setAreaId(1);
        user.setArea(area);
        user.setUserName("test");
        user.setEmail("666666@QQ.com");
        user.setAddr("皇后广场");
        user.setGender("男");
        user.setEnableStatus(1);
        user.setCreateTime(new Date());
        user.setLastEditTime(new Date());
        user.setPhone("13585848476");
        int effectedNum = userDao.insert(user);
        assertEquals(1,effectedNum);
    }

    @Test
    public void testUpdateUser(){
        User user = new User();
        user.setUserId(3L);
        user.setUserName("晓丽");
        user.setAddr("皇后广场");
        user.setGender("女");
        user.setCreateTime(new Date());
        user.setLastEditTime(new Date());
        int effectedNum = userDao.update(user);
        assertEquals(1,effectedNum);
    }

    @Test
    public void testDelete(){

        int effectedNum = userDao.delete(4L);
        assertEquals(1,effectedNum);
    }

    @Test
    public void testSelectByUserId(){
        User user = userDao.selectByUserId(3);
        System.out.println(user.getUserName());
        System.out.println(user.getPhone());
        System.out.println(user.getArea().getAreaName());
        System.out.println(user.getAddr());
    }

    @Test
    public void testSelectAll(){
        List<User> userList = userDao.selectAll();
        System.out.println(userList.size());
    }

    @Test
    public void testSelectByAreaId(){
        List<User> userList = userDao.selectByAreaId(1);
        System.out.println(userList.size());
    }
    @Test
    public void testgetUserIdbyUserName(){
        User user = userDao.getUserIdByUserName("Lisa");
        System.out.println(user.getUserId());
    }
}
