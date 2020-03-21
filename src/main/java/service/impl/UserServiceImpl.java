package service.impl;

import dao.UserAuthDao;
import dao.UserDao;
import dto.UserExecution;
import entity.User;
import entity.UserAuth;
import enums.UserStateEnum;
import exceptions.UserOperationExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.UserService;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserAuthDao userAuthDao;



    @Override
    @Transactional
    public UserExecution addUser(User user, UserAuth userAuth) {
        if(user==null){
            return new UserExecution(UserStateEnum.NULL_USER);
        }
        try{
            //给用户信息赋予初值
            user.setEnableStatus(1);
            user.setCreateTime(new Date());
            user.setLastEditTime(new Date());
            //添加用户信息
            int effectedNum = userDao.insert(user);
            if(effectedNum<=0){
                throw new UserOperationExecution(("用户创建失败"));
            }else{
                User user1 = userDao.getUserIdByUserName(user.getUserName());
                if(userAuth==null){
                    return new UserExecution(UserStateEnum.NULL_ACCOUNT);
                }
                //这里的UserId从传过来的User存到数据库中，在取出它的ID
                user.setUserId(user1.getUserId());
                userAuth.setUser(user);
                userAuth.setCreateTime(new Date());
                userAuth.setLastEditTime(new Date());
                effectedNum = userAuthDao.insert(userAuth);
                if(effectedNum<=0){
                    throw new UserOperationExecution("添加账户信息与密码出错");
                }
            }
        }catch (Exception e){
            throw new UserOperationExecution("addUser error" + e.getMessage());
        }
        return new UserExecution(UserStateEnum.SUCCESS,user);
    }

    @Override
    public User getByUserId(long UserId) {
        return userDao.selectByUserId(UserId);
    }

    @Override
    public UserExecution modify(User user,UserAuth userAuth) {
        if(user==null||user.getUserId()==null ||userAuth==null){
            return new UserExecution(UserStateEnum.NULL_USER);
        }else{
            try{
                user.setUserId(2L);
                user.setLastEditTime(new Date());
                userAuth.setUser(user);
                userAuth.setLastEditTime(new Date());
                UserAuth oldUserAuth = userAuthDao.queryUserByUserId(2);
                int effectedNum = userDao.update(user);
                int effectedNum1 = userAuthDao.updateUserAuth(user.getUserId(),oldUserAuth.getUsername(),oldUserAuth.getPassword(),userAuth.getPassword(),new Date());
                if(effectedNum<=0||effectedNum1<=0){
                    return new UserExecution(UserStateEnum.INNER_ERROR);
                }else{
                    user = userDao.selectByUserId(user.getUserId());
                    return  new UserExecution(UserStateEnum.SUCCESS,user);
                }
            }catch (Exception e){
                throw new UserOperationExecution("modifyUser error" + e.getMessage());
            }
        }
    }

    @Override
    public UserExecution getUserListByAreaId(int areaId) {
        List<User> userList = userDao.selectByAreaId(areaId);
        UserExecution ue = new UserExecution();
        if(userList!=null){
            ue.setUserList(userList);
        }else{
            ue.setState(UserStateEnum.INNER_ERROR.getState());
        }
        return ue;
    }

    @Override
    public UserExecution getAllUserList() {
        List<User> userList = userDao.selectAll();
        UserExecution ue = new UserExecution();
        if(userList!=null){
            ue.setUserList(userList);
        }else{
            ue.setState(UserStateEnum.INNER_ERROR.getState());
        }
        return ue;
    }

    @Override
    public UserExecution addDoctor(User user) {
        if(user==null||user.getUserId()==null){
            return new UserExecution(UserStateEnum.NULL_USER);
        }else{
            try{
                System.out.println(user.getDoctor().getDoctorId());
                user.setLastEditTime(new Date());
                int effectedNum = userDao.update(user);
                if(effectedNum<=0){
                    return new UserExecution(UserStateEnum.INNER_ERROR);
                }else{
                    user = userDao.selectByUserId(user.getUserId());
                    return new UserExecution(UserStateEnum.SUCCESS,user);
                }
            }catch (Exception e){
                throw new UserOperationExecution("addDoctor error" + e.getMessage());
            }
        }
    }

    @Override
    public UserExecution getByDoctorId(Long doctorId) {
        List<User> userList = userDao.selectByDoctorId(doctorId);
        try {
            if (userList.size() > 0) {
                return new UserExecution(UserStateEnum.SUCCESS, userList);
            } else {
                return new UserExecution(UserStateEnum.NULL_USER);
            }
        }catch (Exception e){
            throw new UserOperationExecution("query doctor error" + e.getMessage());
        }
    }
}
