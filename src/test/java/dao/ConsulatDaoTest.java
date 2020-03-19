package dao;

import BaseTest.BaseTest;
import entity.Consultation;
import entity.Doctor;
import entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ConsulatDaoTest extends BaseTest {
    @Autowired
    private ConsultationDao consultationDao;

    @Test
    public void testInsert(){
        Consultation consultation = new Consultation();
        consultation.setUserId(9L);
        consultation.setDoctorId(3L);
        consultation.setCreateTime(new Date());
        consultation.setLastEditTime(new Date());
        consultation.setSymptom("感冒，头晕，流鼻涕");
        consultation.setStatus("未查阅");
        int effectedNum = consultationDao.insert(consultation);
        assertEquals(1,effectedNum);
    }

    @Test
    public void testUpdate(){
        Consultation consultation = new Consultation();
        consultation.setUserId(9L);
        consultation.setDoctorId(3L);
        consultation.setComment("多喝热水");
        consultation.setStatus("已建议");
        int effectedNum = consultationDao.update(consultation);
        assertEquals(1,effectedNum);
    }
    @Test
    public void testSelectbyUserId(){
        List<Consultation> consultationList = consultationDao.selectByUserId(3L);
        System.out.println(consultationList.size());
        System.out.println(consultationList.get(0).getDoctor().getDoctorName());
    }
    @Test
    public void testSelectByDoctorId(){
        List<Consultation> consultationList = consultationDao.selectByDoctorId(3L);
        System.out.println(consultationList.size());
        System.out.println(consultationList.get(0).getUser().getPhone());
    }
}
