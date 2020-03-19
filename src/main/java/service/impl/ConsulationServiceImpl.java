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
    public List<Consultation> getConsultByUserId(long userId) {
        return consultationDao.selectByUserId(userId);
    }
}
