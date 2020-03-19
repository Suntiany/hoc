package service;

import entity.Consultation;

import java.util.List;

public interface ConsultationService {
    int insert(Consultation consultation);
    List<Consultation> getConsultByUserId(long userId);
}
