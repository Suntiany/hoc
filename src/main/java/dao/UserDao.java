package dao;

import entity.Doctor;
import entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    int insert(User user);
    int update(User user);
    int delete(long userId);
    User getUserIdByUserName(String userName);
    User selectByUserId(long userId);
    List<User> selectAll();
    List<User> selectByAreaId(int areaId);
    List<User> selectByDoctorId(long doctorId);

    /**
     * 医生系统条件查询用户的账号 ：可输入的条件有：用户名（模糊），用户张账号状态
     */
    List<User> queryUserList(@Param("userCondition") User userCondition,@Param("doctorId") long doctorId);
}
