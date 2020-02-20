package dao;

import BaseTest.BaseTest;
import entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AreaTest extends BaseTest {
    @Autowired
    private AreaDao areaDao;

    @Test
    public void testQueryArea(){
        List<Area> areaList = areaDao.queryArea();
        System.out.println("===========================");
        System.out.println(areaList.size());
        System.out.println("===========================");
        assertEquals(5,areaList.size());
    }
}