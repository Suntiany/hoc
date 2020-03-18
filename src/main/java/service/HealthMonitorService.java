package service;

import entity.HealthMonitor;

import java.util.List;

public interface HealthMonitorService {
    /**
     * 插入一条健康信息
     * @param healthMonitor
     * @return
     */
    int insertHealthMonitor(HealthMonitor healthMonitor);

    /**
     * 查询用户个人健康信息
     */
    List<HealthMonitor> getHealthMonitorByUserId(long UserId);

    /**
     * 清空记录
     */
    int deleteInfoByUser(long UserId);
}
