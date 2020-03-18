package dao;

import entity.HealthMonitor;

import java.util.List;

public interface HealthMonitorDao {
    /**
     * 增加一条健康监控信息
     */
    int insertHealthMonitor(HealthMonitor healthMonitor);

    /**
     * 查询自己的健康监控信息，并返回到前台
     */
    List<HealthMonitor> selectHealthMonitor(long userId);

    /**
     * 清空自己的健康信息
     */
    int deleteInfoByUserId(long userId);
}
