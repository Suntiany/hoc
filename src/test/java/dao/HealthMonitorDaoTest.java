package dao;

import BaseTest.BaseTest;
import entity.HealthMonitor;
import entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class HealthMonitorDaoTest extends BaseTest {
    @Autowired
    private HealthMonitorDao healthMonitorDao;

    @Test
    public void testInsertHealthMonitor(){
        HealthMonitor healthMonitor = new HealthMonitor();
        User user = new User();
        user.setUserId(2L);
        healthMonitor.setUser(user);
        healthMonitor.setCreateTime(new Date());
        healthMonitor.setLastEditTime(new Date());
        healthMonitor.setBloodGlucose(50);
        healthMonitor.setBloodPressureLow(80);
        healthMonitor.setBloodPressureHigh(120);
        healthMonitor.setHeartRate(70);
        int effectedNum = healthMonitorDao.insertHealthMonitor(healthMonitor);
        assertEquals(1,effectedNum);
    }

    @Test
    public void testSelectMonitor(){
        List<HealthMonitor> healthMonitorList = healthMonitorDao.selectHealthMonitor(2L);
        System.out.println(healthMonitorList.size());
        System.out.println(healthMonitorList.get(0).getUser().getUserId());
    }

    @Test
    public void testDelete(){
        int effectedNum = healthMonitorDao.deleteInfoByUserId(9L);
        assertEquals(2,effectedNum);
    }
}
