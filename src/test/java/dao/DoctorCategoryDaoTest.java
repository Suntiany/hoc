package dao;

import BaseTest.BaseTest;
import entity.DoctorCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DoctorCategoryDaoTest extends BaseTest {
    @Autowired
    private DoctorCategoryDao doctorCategoryDao;
    @Test
    public void testBatchInsertDoctorCategory(){
        DoctorCategory doctorCategory = new DoctorCategory();
        doctorCategory.setDoctorCategoryName("测试科室类别1");
        doctorCategory.setPriority(1);
        doctorCategory.setCreateTime(new Date());
        doctorCategory.setLastEditTime(new Date());
        doctorCategory.setHospitalId(17L);
        DoctorCategory doctorCategory2 = new DoctorCategory();
        doctorCategory2.setDoctorCategoryName("测试科室类别2");
        doctorCategory2.setPriority(1);
        doctorCategory2.setCreateTime(new Date());
        doctorCategory2.setLastEditTime(new Date());
        doctorCategory2.setHospitalId(17L);
        List<DoctorCategory> doctorCategoryList = new ArrayList<DoctorCategory>();
        doctorCategoryList.add(doctorCategory);
        doctorCategoryList.add(doctorCategory2);
        int effectedNum = doctorCategoryDao.batchInsertDoctorCategory(doctorCategoryList);
        assertEquals(2,effectedNum);
    }
    @Test
    public void testDeleteDoctorCategory() {
        long hospitalId = 17;
        List<DoctorCategory> doctorCategoryList = doctorCategoryDao.queryDoctorCategoryList(hospitalId);
        for(DoctorCategory dc:doctorCategoryList){
            if("测试科室类别1".equals(dc.getDoctorCategoryName())||"测试科室类别2".equals(dc.getDoctorCategoryName())){
                int effectedNum = doctorCategoryDao.deleteDoctorCategory(dc.getDoctorCategoryId(),hospitalId);
                assertEquals(1,effectedNum);
            }
        }
    }
}
