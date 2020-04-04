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


    /**
     * 通过User表里每一个用户的DoctorId值来对用户属于哪一个医生负责进行分类，用于医生系统里的医生获取自己的用户列表
     * @param doctorId
     * @return
     */
    UserExecution getByDoctorId(Long doctorId);


    /**
     * 医生通过搜索框输入用户名可以进行模糊查询用户信息
     */
    UserExecution getByDoctorIdAndUserCondition(User userCondition,Long doctorId);


    /**
     * 超级管理员更改User 账户状态
     */
    UserExecution suModify(User user);
}
