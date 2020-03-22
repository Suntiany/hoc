package dao;

import entity.Consultation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ConsultationDao {
    /**
     * 新增一条问诊信息，由用户提交
     */
    int insert(Consultation consultation);


    /**
     * 医生收到问诊信息后，填写建议
     */
    int update(Consultation consultation);


    /**
     * 用户通过UserId查询自己提交的问诊信息
     */
    List<Consultation>  selectByUserId(Long userId);


    /**
     * 医生通过自己的DoctorId 查询与自己相关的用户的问诊
     */
    List<Consultation> selectByDoctorId(@Param("doctorId") Long doctorId,@Param("userId") Long userId);

    /**
     * 通过ConsultId来获取问诊单信息
     */
    Consultation selectByConsultId(long consultId);
}
