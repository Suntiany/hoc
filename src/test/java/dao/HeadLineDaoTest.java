package dao;

import BaseTest.BaseTest;
import entity.HeadLine;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class HeadLineDaoTest extends BaseTest {
    @Autowired
    private HeadLineDao headLineDao;

    @Test
    public void testHeadLineDao(){
        List<HeadLine> headLineList = headLineDao.queryHeadLine(new HeadLine());
        System.out.println(headLineList.size());
    }
}
