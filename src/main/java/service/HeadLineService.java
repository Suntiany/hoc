package service;

import dto.HeadLineExecution;
import dto.ImageHolder;
import entity.HeadLine;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


public interface HeadLineService {
    /**
     * 根据传入的条件返回指定的头条列表
     * @param headLineCondition
     * @return
     * @throws IOException
     */
    List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException;

    /**
     * 添加头条信息，并存储头条图片
     */
    HeadLineExecution addHeadLine(HeadLine headLine, ImageHolder thumbnail);

    /**
     * 修改头条信息
     *
     * @param headLine
     * @param thumbnail
     * @return
     */
    HeadLineExecution modifyHeadLine(HeadLine headLine, ImageHolder thumbnail);

    /**
     * 删除单条头条
     *
     * @param headLineId
     * @return
     */
    HeadLineExecution removeHeadLine(long headLineId);

    /**
     * 批量删除头条
     *
     * @param headLineIdList
     * @return
     */
    HeadLineExecution removeHeadLineList(List<Long> headLineIdList);
}
