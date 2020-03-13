package service.impl;

import dao.UserAuthDao;
import entity.User;
import entity.UserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.UserAuthService;

import java.util.Date;

@Service
public class UserAuthServiceImpl implements UserAuthService {
    @Autowired
    private UserAuthDao userAuthDao;
    @Override
    public UserAuth Login(String username, String password) {
        //UserId 也要从session 中获取
        UserAuth user = userAuthDao.login(username,password);
        return user;
    }

    @Override
    public int changePass(Long userId, String username, String password, String newPassword) {
        Date date = new Date();
        int effectedNum = userAuthDao.updateUserAuth(userId,username,password,newPassword,date);
        return effectedNum;
    }

    @Override
    public UserAuth getUserAuthByUserId(long userId) {
        return userAuthDao.queryUserByUserId(userId);
    }
}
