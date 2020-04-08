package service.impl;

import dao.ReplyDao;
import entity.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.ReplyService;

import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    private ReplyDao replyDao;


    @Override
    public int insertReply(Reply reply) {
        try {
            int effectedNum = replyDao.insert(reply);
            if (effectedNum < 0) {
                return -1;
            } else {
                return effectedNum;
            }
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public List<Reply> selectByConsultId(long consultId) {
        List<Reply> replyList = replyDao.selectByConsultId(consultId);
        return replyList;
    }
}