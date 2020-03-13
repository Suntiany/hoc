package dao;

import entity.AreaAuth;
import entity.UserAuth;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface UserAuthDao {
    int insert(UserAuth userAuth);
    /**
     * 通过账号和密码查询对应的信息，登录用
     */
    UserAuth login(@Param("username") String username, @Param("password") String password);

    /**
     * 通过userId,username,password更改密码
     */
    int updateUserAuth(@Param("userId") Long userId,@Param("username") String username,@Param("password") String password,@Param("newPassword") String newPassword,@Param("lastEditTime") Date lastEditTIme);

    /**
     * 通过userId查询对应的账号密码
     */
    UserAuth queryUserByUserId(@Param("userId") long userId);
}
