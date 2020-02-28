package dao;

import BaseTest.BaseTest;
import entity.DoctorImg;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DoctorImgDaoTest extends BaseTest {
    @Autowired
    private DoctorImgDao doctorImgDao;

    @Test
    public void testBatchInsertDoctorImg() throws Exception{
        DoctorImg doctorImg = new DoctorImg();
        doctorImg.setImgAddr("图片1");
        doctorImg.setImgAddr("测试1");
        doctorImg.setCreateTime(new Date());
        doctorImg.setImgDesc("简述");
        doctorImg.setPriority(1);
        doctorImg.setDoctorId(3L);
        DoctorImg doctorImg1 = new DoctorImg();
        doctorImg1.setImgAddr("图片1");
        doctorImg1.setImgAddr("测试1");
        doctorImg1.setCreateTime(new Date());
        doctorImg1.setImgDesc("简述");
        doctorImg1.setDoctorId(3L);
        doctorImg1.setPriority(1);
        List<DoctorImg> doctorImgList = new ArrayList<DoctorImg>();
        doctorImgList.add(doctorImg);
        doctorImgList.add(doctorImg1);
        int effectedNum = doctorImgDao.batchInsertDoctorImg(doctorImgList);
        assertEquals(2,effectedNum);
    }

    @Test
    public void testqueryDoctorImgList() throws Exception{
        List<DoctorImg> doctorImgList = new ArrayList<DoctorImg>();
        doctorImgList = doctorImgDao.queryDoctorImgList(3L);
        System.out.println(doctorImgList.size());
    }

    @Test
    public void testDeleteDoctorImg() {
        Long doctorId = 3L;
        int effectedNum = doctorImgDao.deleteDoctorImgByDoctorId(doctorId);
        System.out.println(effectedNum);
    }
}
