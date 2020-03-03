package dao;

import BaseTest.BaseTest;
import entity.AreaAuth;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AreaAuthDaoTest extends BaseTest {
    @Autowired
     private AreaAuthDao areaAuthDao;
    @Test
    public void testLogin(){
        String username = "admin";
        String password="123456";
        AreaAuth areaAuth = new AreaAuth();
        areaAuth = areaAuthDao.login(username,password);
        System.out.println(areaAuth.getArea().getAreaName());
        System.out.println(areaAuth.getUsername());
    }
}
