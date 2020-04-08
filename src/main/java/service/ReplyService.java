package service;

import entity.Reply;

import java.util.List;

public interface ReplyService {
    int insertReply(Reply reply);
    List<Reply> selectByConsultId(long consultId);
}
