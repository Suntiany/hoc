package dao;

import BaseTest.BaseTest;
import entity.Doctor;
import entity.DoctorCategory;
import entity.Hospital;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DoctorDaoTest extends BaseTest {
    @Autowired
    private DoctorDao doctorDao;

    @Test
    public void testInsertADoctor() throws Exception {
        Hospital hospital = new Hospital();
        hospital.setHospitalId(17L);
        DoctorCategory doctorCategory = new DoctorCategory();
        doctorCategory.setDoctorCategoryId(1L);
        Doctor doctor = new Doctor();
        doctor.setHospital(hospital);
        doctor.setDoctorCategory(doctorCategory);
        doctor.setDoctorDesc("谢医生是德高望重的医生");
        doctor.setDoctorName("谢广坤");
        doctor.setImgAddr("test");
        doctor.setCreateTime(new Date());
        doctor.setPriority(1);
        doctor.setEnableStatus(1);
        int effectedNum = doctorDao.insertDoctor(doctor);
        assertEquals(1,effectedNum);
    }

    @Test
    public void testGetDoctorById() {
        Doctor doctor = doctorDao.queryDoctorById(3L);
        System.out.println(doctor.getDoctorName());
        System.out.println(doctor.getDoctorImgList().get(0).getImgAddr());
        System.out.println(doctor.getDoctorCategory().getDoctorCategoryName());
    }

    @Test
    public void testUpdateDoctor() {
        Doctor doctor = new Doctor();
        DoctorCategory doctorCategory = new DoctorCategory();
        Hospital hospital = new Hospital();
        doctorCategory.setDoctorCategoryId(1L);
        hospital.setHospitalId(17L);
        doctor.setDoctorCategory(doctorCategory);
        doctor.setHospital(hospital);
        doctor.setDoctorId(4L);
        doctor.setDoctorDesc("十万伏特电疗大师");
        int effectedNum = doctorDao.updateDoctor(doctor);
    }
    @Test
    public void testGetDoctorByDoctorName(){
        String DoctorName = "杨永信";
        Doctor doctor = doctorDao.getDoctorIdByDoctorName(DoctorName);
        System.out.println(doctor.getDoctorId());
    }

    @Test
    public void testSuGetDoctorList(){
        List<Doctor> doctorList = doctorDao.suGetDoctorList();
        System.out.println(doctorList.size());
        System.out.println(doctorList.get(0).getHospital().getHospitalName());
        System.out.println(doctorList.get(0).getDoctorCategory().getDoctorCategoryName());
    }
}
