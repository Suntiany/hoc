package dao;

import entity.DoctorAuth;
import entity.UserAuth;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface DoctorAuthDao {
    int insert(DoctorAuth doctorAuth);
    /**
     * 通过账号和密码查询对应的信息，登录用
     */
    DoctorAuth login(@Param("username") String username, @Param("password") String password);

    /**
     * 通过doctorId,username,password更改密码
     */
    int updateDoctorAuth(@Param("doctorId") Long doctorId,@Param("username") String username,@Param("password") String password,@Param("newPassword") String newPassword,@Param("lastEditTime") Date lastEditTIme);

    /**
     * 通过doctorId查询对应的账号密码
     */
    DoctorAuth queryDoctorByDoctorId(@Param("doctorId") long doctorId);
}
