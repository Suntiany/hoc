package dao;

import BaseTest.BaseTest;
import entity.Consultation;
import entity.Reply;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ReplyDaoTest extends BaseTest {
    @Autowired
    private ReplyDao replyDao;

    @Test
    public void testInsertReply(){
        Consultation consultation = new Consultation();
        consultation.setConsultId(13);
        Reply reply = new Reply();
        reply.setConsultation(consultation);
        reply.setMessage("回复test2");
        reply.setReplyType(1);
        reply.setCreateTime(new Date());
        int effectedNum = replyDao.insert(reply);
        assertEquals(1,effectedNum);
    }

    @Test
    public void testSelectReplyByConsultId(){
        long consultId = 13L;
        List<Reply> replyList = replyDao.selectByConsultId(consultId);
        System.out.println(replyList.size());
        System.out.println(replyList.get(0).getReplyId());
        System.out.println(replyList.get(0).getMessage());
    }
}
