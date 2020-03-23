package service.impl;

import dao.DoctorAuthDao;
import entity.DoctorAuth;
import entity.UserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.DoctorAuthService;

import java.util.Date;

@Service
public class DoctorAuthServiceImpl implements DoctorAuthService {
    @Autowired
    private DoctorAuthDao doctorAuthDao;
    @Override
    public DoctorAuth login(String username, String password) {
        DoctorAuth doctorAuth = doctorAuthDao.login(username,password);
        return doctorAuth;
    }

    @Override
    public int changePass(Long doctorId, String username, String password, String newPassword) {
        Date date = new Date();
        int effectedNum = doctorAuthDao.updateDoctorAuth(doctorId,username,password,newPassword,date);
        return effectedNum;
    }

    @Override
    public DoctorAuth getDoctorAuthByDoctorId(long doctorId) {
        return doctorAuthDao.queryDoctorByDoctorId(doctorId);
    }
}
