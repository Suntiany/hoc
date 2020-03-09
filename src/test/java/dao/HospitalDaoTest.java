package dao;

import BaseTest.BaseTest;
import entity.Area;
import entity.Hospital;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

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
        hospital.setLastEditTime(new Date());
        hospital.setPriority(1);
        hospital.setEnableStatus(1);
        hospital.setOwnerName("张医生");
        hospital.setPhone("1383838438");
        int effectedNum = hospitalDao.insertHospital(hospital);
        assertEquals(1,effectedNum);
    }

    @Test
    public void testUpdateShop() {
        Hospital hospital = new Hospital();
        hospital.setHospitalId(17L);
        hospital.setHospitalName("一院名字改");
        hospital.setOwnerName("张医生改");
        int effectedNum = hospitalDao.updateHospital(hospital);
        assertEquals(1,effectedNum);
    }

    @Test
    public void QueryByHospitalId(){
        long hospitalId = 25;
        Hospital hospital = hospitalDao.queryByHospitalId(hospitalId);
        System.out.println(hospital.getArea().getAreaName());
        System.out.println(hospital.getArea().getAreaId());
        System.out.println(hospital.getOwnerName());
    }

    @Test
    public void testQueryHospitalList() {
        Hospital hospitalCondition = new Hospital();
        Area area = new Area();
        area.setAreaId(1);
        hospitalCondition.setArea(area);
        hospitalCondition.setHospitalName("二院");
        List<Hospital> hospitalList = hospitalDao.queryHospitalList(hospitalCondition,0,5);
        int count = hospitalDao.queryHospitalCount(hospitalCondition);
        System.out.println("医院列表的大小：" +hospitalList.size());
        System.out.println("医院总数:"+ count);
    }

    @Test
    public void testQueryAllHospitalList() {
        Hospital hospitalCondition = new Hospital();
        hospitalCondition.setEnableStatus(0);
        List<Hospital> hospitalList = hospitalDao.queryHospitalList(hospitalCondition,0,10);
        System.out.println("医院列表的大小：" +hospitalList.size());
    }
}
