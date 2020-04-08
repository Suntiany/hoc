package dao;

import entity.Consultation;
import entity.Reply;

import java.util.List;

public interface ReplyDao {
    int insert(Reply reply);
    List<Reply> selectByConsultId(long consultId);
}
