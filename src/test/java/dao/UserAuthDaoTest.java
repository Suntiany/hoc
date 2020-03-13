package dao;

import BaseTest.BaseTest;
import entity.User;
import entity.UserAuth;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class UserAuthDaoTest extends BaseTest {
    @Autowired
    private UserAuthDao userAuthDao;

    @Test
    public void testRegister(){
        User user = new User();
        user.setUserId(1L);
        UserAuth userAuth = new UserAuth();
        userAuth.setUser(user);
        userAuth.setCreateTime(new Date());
        userAuth.setLastEditTime(new Date());
        userAuth.setPassword("123456");
        userAuth.setUsername("user");
        int effectedNum = userAuthDao.insert(userAuth);
        assertEquals(1,effectedNum);
    }

    @Test
    public void testUserLogin(){
        UserAuth user = userAuthDao.login("123456","admin123");
        System.out.println(user.getLocalUserId());
        System.out.println(user.getUser().getUserId());
    }

    @Test
    public void testUpdatePassword(){
        Date date = new Date();
        int effectedNum = userAuthDao.updateUserAuth(1L,"user","123456","12345678",date);
        assertEquals(1,effectedNum);
    }

    @Test
    public void testQueryUserById(){
        UserAuth userAuth = userAuthDao.queryUserByUserId(1L);
        System.out.println(userAuth.getUsername());
        System.out.println(userAuth.getPassword());
    }

    @Test
    public void testInsertinsert(){
        UserAuth userAuth = new UserAuth();
        User user = new User();
        user.setUserId(2L);
        userAuth.setUser(user);
        userAuth.setUsername("123456");
        userAuth.setPassword("123456");
        userAuth.setCreateTime(new Date());
        userAuth.setLastEditTime(new Date());
        int effectedNum = userAuthDao.insert(userAuth);
        assertEquals(1,effectedNum);
    }
}
