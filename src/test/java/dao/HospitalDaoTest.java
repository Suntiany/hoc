package dao;

import BaseTest.BaseTest;
import entity.Area;
import entity.Hospital;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class HospitalDaoTest extends BaseTest {
    @Autowired
    private HospitalDao hospitalDao;
    @Test
    public void testInsertHospital() {
        Hospital hospital = new Hospital();
        Area area = new Area();
        area.setAreaId(1);
        hospital.setArea(area);
        hospital.setHospitalName("一院");
        hospital.setHospitalDesc("这里是一号医院，这里有着最好的医疗资源");
        hospital.setHospitalAddr("一街道");
        hospital.setCreateTime(new Date());
        hospital.setPriority(1);
        hospital.setEnableStatus(1);
        hospital.setOwnerId("张医生");
        int effectedNum = hospitalDao.insertHospital(hospital);
        assertEquals(1,effectedNum);
    }
}
