package service;

import dto.ImageHolder;
import entity.Consultation;

import java.util.List;

public interface ConsultationService {
    int insert(Consultation consultation, ImageHolder thumbnail);
    int update(Consultation consultation);
    List<Consultation> getConsultByUserId(long userId);
    List<Consultation> getConsultByDoctorId(long doctorId,long userId);
    Consultation getConsultByConsultId(long consultId);
}
