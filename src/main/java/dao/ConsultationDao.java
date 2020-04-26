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
     * 用户通过UserId查询自己提交的所有的问诊信息
     */
    List<Consultation>  selectByUserId(Long userId);

    /**
     * 用户通过条件查询（症状内容的模糊查询）获得自己提交过的问诊单列表
     */
    List<Consultation> queryByUserId(@Param("consultCondition") Consultation consultCondition);

    /**
     * 医生通过自己的DoctorId 查询与自己相关的具体某一位用户的所有问诊信息
     */
    List<Consultation> selectByDoctorId(@Param("doctorId") Long doctorId,@Param("userId") Long userId);

    /**
     * 医生通过自己的DoctorId 与对应用户的UserId 加上某些条件（症状内容的模糊查询） 获取这个用户的特定的问诊单列表
     */
    List<Consultation> queryByDoctorId(@Param("consultCondition") Consultation consultCondition);

    /**
     * 通过ConsultId来获取问诊单信息
     */
    Consultation selectByConsultId(long consultId);


}
