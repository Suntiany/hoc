package service;

import entity.DoctorAuth;

public interface DoctorAuthService {
    /**
     * 医生通过账号密码来登录
     */
    DoctorAuth login(String username,String password);

    /**
     * 医生通过自己的doctorId来更改登录密码
     */
    int changePass(Long doctorId,String username,String password,String newPassword);

    DoctorAuth getDoctorAuthByDoctorId(long doctorId);
}
