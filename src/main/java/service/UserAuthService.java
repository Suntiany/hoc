package service;

import entity.UserAuth;

public interface UserAuthService {
    /**
     * 用户通过账号密码登录
     */
    UserAuth Login(String username,String password);

    /**
     * 用户通过自己的UserId来更改密码
     */
    int changePass(Long userId,String username,String password,String newPassword);

    UserAuth getUserAuthByUserId(long userId);
}
