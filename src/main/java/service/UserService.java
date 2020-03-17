package service;

import dto.HospitalExecution;
import dto.UserExecution;
import entity.User;
import entity.UserAuth;

public interface UserService {
    /**
     * 新增一个用户
     * @param user
     * @return
     */
    UserExecution addUser(User user, UserAuth userAuth);

    /**
     * 通过UserId获取用户的信息，用于用户修改个人信息
     * @param UserId
     * @return
     */
    User getByUserId(long UserId);

    /**
     * 用户修改个人信息
     * @param user
     * @return
     */
    UserExecution modify(User user,UserAuth userAuth);

    /**
     * 获取某个areaId下的所有用户
     * @param areaId
     * @return
     */
    UserExecution getUserListByAreaId(int areaId);

    /**
     * 获取所有使用本系统的用户
     * @return
     */
    UserExecution getAllUserList();

    /**
     * 为用户指派一位家庭医生（插入一位家庭医生的id）
     * @param user
     * @return
     */
    UserExecution addDoctor(User user);
}
