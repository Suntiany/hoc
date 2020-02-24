package service;

import BaseTest.BaseTest;
import dto.HospitalExecution;
import dto.ImageHolder;
import entity.Area;
import entity.Hospital;
import enums.HospitalStateEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class HospitalServiceTest extends BaseTest {
    @Autowired
    private HospitalService hospitalService;

    @Test
    public void testAddShop() throws FileNotFoundException {
        Hospital hospital = new Hospital();
        Area area = new Area();
        area.setAreaId(1);
        hospital.setHospitalId(1L);
        hospital.setArea(area);
        hospital.setHospitalName("二院");
        hospital.setHospitalDesc("这里是二号医院，这里有着最好的医疗资源");
        hospital.setHospitalAddr("二街道");
        hospital.setCreateTime(new Date());
        hospital.setLastEditTime(new Date());
        hospital.setPriority(1);
        hospital.setEnableStatus(1);
        hospital.setOwnerName("王医生");
        hospital.setPhone("1583838438");
        File hospitalImg = new File("C:\\Users\\19429\\Desktop\\Users\\baidu\\work\\image\\upload\\images\\item\\shop\\1\\2017092400140169232.jpg");
        InputStream is = new FileInputStream(hospitalImg);
        ImageHolder imageHolder = new ImageHolder(hospitalImg.getName(),is);
        HospitalExecution se = hospitalService.addHospital(hospital,imageHolder);
        assertEquals(HospitalStateEnum.CHECK.getState(),se.getState());
    }

    @Test
    public void testQueryHospitalListAndCount() {
        Hospital hospitalCondition = new Hospital();
        Area area = new Area();
        area.setAreaId(1);
        hospitalCondition.setArea(area);
        HospitalExecution he = hospitalService.getHospitalList(hospitalCondition,0,5);
        System.out.println(he.getCount());
    }
}
