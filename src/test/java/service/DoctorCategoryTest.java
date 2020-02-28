package service;

import BaseTest.BaseTest;
import entity.DoctorCategory;
import entity.Hospital;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DoctorCategoryTest extends BaseTest {
    @Autowired
    private DoctorCategoryService doctorCategoryService;
    @Test
    public void testDoctorCategoryList(){
        Long HospitalId = 17L;
        List<DoctorCategory> doctorCategoryList = doctorCategoryService.getDoctorCategoryList(HospitalId);
        System.out.println(doctorCategoryList.size());
    }
}
