package dao;

import BaseTest.BaseTest;
import entity.Doctor;
import entity.DoctorAuth;
import entity.User;
import entity.UserAuth;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class DoctorAuthDaoTest extends BaseTest {
    @Autowired
    private DoctorAuthDao doctorAuthDao;

    @Test
    public void testRegister(){
        Doctor doctor = new Doctor();
        doctor.setDoctorId(3L);
        DoctorAuth doctorAuth = new DoctorAuth();
        doctorAuth.setDoctor(doctor);
        doctorAuth.setCreateTime(new Date());
        doctorAuth.setLastEditTime(new Date());
        doctorAuth.setPassword("123456");
        doctorAuth.setUsername("xgk");
        int effectedNum = doctorAuthDao.insert(doctorAuth);
        assertEquals(1,effectedNum);
    }

    @Test
    public void testDoctorLogin(){
        DoctorAuth doctorAuth = doctorAuthDao.login("谢广坤","123456");
        System.out.println(doctorAuth.getLocalDoctorId());
        System.out.println(doctorAuth.getDoctor().getDoctorId());
        System.out.println(doctorAuth.getDoctor().getDoctorName());
    }

    @Test
    public void testUpdatePassword(){
        Date date = new Date();
        int effectedNum = doctorAuthDao.updateDoctorAuth(3L,"xgk","123456","12345678",date);
        assertEquals(1,effectedNum);
    }

    @Test
    public void testQueryUserById(){
        DoctorAuth doctorAuth = doctorAuthDao.queryDoctorByDoctorId(3L);
        System.out.println(doctorAuth.getUsername());
        System.out.println(doctorAuth.getPassword());
    }

}
