package service;

import entity.AreaAuth;

public interface AreaAuthService {
    /**
     * 通过账号密码登录
     */
    AreaAuth Login(String username,String password);

    /**
     * 修改账号密码
     */

}
