package service.impl;

import dao.ConsultationDao;
import dto.ImageHolder;
import entity.Consultation;
import entity.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.ConsultationService;
import service.UserService;
import util.ImageUtil;
import util.PathUtil;

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
    public int insert(Consultation consultation, ImageHolder thumbnail) {
        if(thumbnail!=null){
            addThumbnail(consultation,thumbnail);
        }
        try{
            int effectedNum = consultationDao.insert(consultation);
            if(effectedNum<=0) {
                throw new RuntimeException("创建问诊失败");
            }
            return effectedNum;
        }catch (Exception e){
            throw new RuntimeException("创建问诊失败" + e.toString());
        }
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

    private void addThumbnail(Consultation consultation, ImageHolder thumbnail) {
        String dest = PathUtil.getHeadLineImagePath();
        String thumbnailAddr = ImageUtil.generateHeadlineImg(thumbnail,dest);
        consultation.setMedicalRecord(thumbnailAddr);
    }
}
