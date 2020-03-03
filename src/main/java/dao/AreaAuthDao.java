package dao;

import entity.AreaAuth;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface AreaAuthDao {
    /**
     * 通过账号和密码查询对应的信息，登录用
     */
    AreaAuth login(@Param("username") String username,@Param("password") String password);

    /**
     * 通过areaId,username,password更改密码
     */
    int updateAreaAuth(@Param("areaId") Long areaId,@Param("username") String username,@Param("password") String password,@Param("newPasswoed") String newPassword,@Param("lastEditTime") Date lastEditTIme);
}
