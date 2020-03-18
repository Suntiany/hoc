package service.impl;

import dao.HealthMonitorDao;
import entity.HealthMonitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.HealthMonitorService;

import java.util.List;
@Service
public class HealthMonitorServiceImpl implements HealthMonitorService {
    @Autowired
    private HealthMonitorDao healthMonitorDao;
    @Override
    public int insertHealthMonitor(HealthMonitor healthMonitor) {
        return healthMonitorDao.insertHealthMonitor(healthMonitor);
    }

    @Override
    public List<HealthMonitor> getHealthMonitorByUserId(long UserId) {
        List<HealthMonitor> healthMonitorList = healthMonitorDao.selectHealthMonitor(UserId);
        return healthMonitorList;
    }

    @Override
    public int deleteInfoByUser(long UserId) {
        int effectedNum = healthMonitorDao.deleteInfoByUserId(UserId);
        return effectedNum;
    }
}
