package service;

import dto.ImageHolder;
import entity.Consultation;

import java.util.List;

public interface ConsultationService {
    int insert(Consultation consultation, ImageHolder thumbnail);
    int update(Consultation consultation);
    List<Consultation> getConsultByUserId(long userId);

    /**
     * 用户系统模糊查询问诊单
     * @param consultation
     * @return
     */
    List<Consultation> queryListOfUser(Consultation consultation);
    List<Consultation> getConsultByDoctorId(long doctorId,long userId);

    /**
     * 医生系统模糊查询用户问诊单
     * @param consultation
     * @return
     */
    List<Consultation> queryListOfDoctor(Consultation consultation);
    Consultation getConsultByConsultId(long consultId);
}
