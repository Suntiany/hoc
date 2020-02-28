package service;

import BaseTest.BaseTest;
import dto.DoctorExecution;
import dto.ImageHolder;
import entity.Doctor;
import entity.DoctorCategory;
import entity.Hospital;
import enums.DoctorStateEnum;
import exceptions.HospitalOperationException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DoctorServiceTest extends BaseTest {
    @Autowired
    private DoctorService doctorService;

    @Test
    public void testAddDoctor() throws HospitalOperationException , FileNotFoundException {
        Doctor doctor = new Doctor();
        Hospital hospital = new Hospital();
        hospital.setHospitalId(17L);
        DoctorCategory doctorCategory = new DoctorCategory();
        doctorCategory.setDoctorCategoryId(1L);
        doctor.setHospital(hospital);
        doctor.setDoctorCategory(doctorCategory);
        doctor.setDoctorName("杨永信");
        doctor.setDoctorDesc("电疗大师");
        doctor.setPriority(2);
        doctor.setCreateTime(new Date());
        doctor.setEnableStatus(DoctorStateEnum.SUCCESS.getState());
        //创建文件缩略图文件流
        File thumbnailFile = new File("C:\\Users\\19429\\Desktop\\Project Picture\\1.jpg");
        InputStream is = new FileInputStream(thumbnailFile);
        ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(), is);
        // 创建两个商品详情图文件流并将他们添加到详情图列表中
        File DoctorImg1 = new File("C:\\Users\\19429\\Desktop\\Project Picture\\1.jpg");
        InputStream is1 = new FileInputStream(DoctorImg1);
        File DoctorImg2 = new File("C:\\Users\\19429\\Desktop\\Project Picture\\1.jpg");
        InputStream is2 = new FileInputStream(DoctorImg2);
        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        productImgList.add(new ImageHolder(DoctorImg1.getName(), is1));
        productImgList.add(new ImageHolder(DoctorImg2.getName(), is2));
        // 添加商品并验证
        DoctorExecution pe = doctorService.addDoctor(doctor, thumbnail, productImgList);
        assertEquals(DoctorStateEnum.SUCCESS.getState(), pe.getState());
    }
}
