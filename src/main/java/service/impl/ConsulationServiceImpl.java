package service.impl;

import dao.ConsultationDao;
import entity.Consultation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.ConsultationService;
import service.UserService;

import java.util.List;

@Service
public class ConsulationServiceImpl implements ConsultationService {
    @Autowired
    private ConsultationDao consultationDao;

    /**
     * 用户新增一条问诊信息
     * @param consultation
     * @return
     */
    @Override
    public int insert(Consultation consultation) {
        return consultationDao.insert(consultation);
    }

    @Override
    public int update(Consultation consultation) {
        int effectedNum = consultationDao.update(consultation);
        return effectedNum;
    }

    @Override
    public List<Consultation> getConsultByUserId(long userId) {
        return consultationDao.selectByUserId(userId);
    }

    @Override
    public List<Consultation> getConsultByDoctorId(long doctorId,long userId) {
        return consultationDao.selectByDoctorId(doctorId,userId);
    }

    @Override
    public Consultation getConsultByConsultId(long consultId) {
        return consultationDao.selectByConsultId(consultId);
    }
}
