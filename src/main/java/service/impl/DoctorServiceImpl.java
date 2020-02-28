package service.impl;

import dao.DoctorDao;
import dao.DoctorImgDao;
import dto.DoctorExecution;
import dto.ImageHolder;
import entity.Doctor;
import entity.DoctorImg;
import enums.DoctorStateEnum;
import exceptions.DoctorOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.DoctorService;
import util.ImageUtil;
import util.PageCalculator;
import util.PathUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    private DoctorDao doctorDao;
    @Autowired
    private DoctorImgDao doctorImgDao;
    @Override
    @Transactional
    //1.处理缩略图，获取缩略图路径并赋值给doctor
    //2.往tb_doctor里写入doctor信息，获取doctorId
    //3.结合doctorId批量处理医生详情图
    //4.将医生详情图列表批量插入到tb_doctor_img中
    public DoctorExecution addDoctor(Doctor doctor, ImageHolder thumbnail, List<ImageHolder> doctorImgList) throws DoctorOperationException {
        //空值判断
        if(doctor!=null && doctor.getHospital()!=null && doctor.getHospital().getHospitalId()!=null) {
            //给医生设置默认属性
            doctor.setCreateTime(new Date());
            doctor.setLastEditTime(new Date());
            //默认合法医生
            doctor.setEnableStatus(1);
            //如果医生缩略图不为空则添加
            if(thumbnail!=null){
                addThumbnail(doctor,thumbnail);
            }
            try{
                //创建医生信息
                int effectedNum = doctorDao.insertDoctor(doctor);
                if(effectedNum<=0) {
                    throw new DoctorOperationException("创建医生失败");
                }
            }catch (Exception e) {
                throw new DoctorOperationException("创建医生失败" + e.toString());
            }
            //如果医生详情图不为空则添加
            if(doctorImgList!=null&&doctorImgList.size()>0) {
                addDoctorImgList(doctor,doctorImgList);
            }
            return new DoctorExecution(DoctorStateEnum.SUCCESS,doctor);
        }else{
            return new DoctorExecution(DoctorStateEnum.EMPTY);
        }
    }

    @Override
    public Doctor getDoctorById(long doctorId) {
        return doctorDao.queryDoctorById(doctorId);
    }

    @Override
    @Transactional
    //1.如果缩略图有值，就处理缩略图
    //2.如果原先存在缩略图则先删除再添加新图，之后获取缩略图相对路径并赋值给doctor
    //3.若医生详情图列表参数有值，对商品详情图片列表进行同样的操作
    //4.将tb_doctor_img下面的关于该医生的详情图记录全部删除
    public DoctorExecution modifyDoctor(Doctor doctor, ImageHolder thumbnail, List<ImageHolder> doctorImgHolderList) throws DoctorOperationException {
        //空值判断
        if(doctor!=null && doctor.getHospital()!=null && doctor.getHospital().getHospitalId()!=null) {
            //给医生设置默认属性
            doctor.setLastEditTime(new Date());
            //若医生缩略图不为空且原有缩略图不为空则删除原有缩略图并添加
            if(thumbnail !=null) {
                Doctor tempDoctor = doctorDao.queryDoctorById(doctor.getDoctorId());
                if(tempDoctor.getImgAddr()!=null) {
                    ImageUtil.deleteFileOrPath(tempDoctor.getImgAddr());
                }
                addThumbnail(doctor,thumbnail);
            }
            //如果有新存入的医生详情图，则将原先的删除，并添加新的图片
            if(doctorImgHolderList!=null && doctorImgHolderList.size()>0) {
                deleteDoctorImgList(doctor.getDoctorId());
                addDoctorImgList(doctor,doctorImgHolderList);
            }
            try{
                //更新医生信息
                int effectedNum = doctorDao.updateDoctor(doctor);
                if(effectedNum<=0) {
                    throw new DoctorOperationException("更新医生信息失败");
                }
                return new DoctorExecution(DoctorStateEnum.SUCCESS,doctor);
            }catch (Exception e) {
                throw new DoctorOperationException("更新医生信息失败" + e.toString());
            }
        }else{
            return new DoctorExecution(DoctorStateEnum.EMPTY);
        }
    }

    private void deleteDoctorImgList(Long doctorId) {
        //根据doctorId获取原来的图片
        List<DoctorImg> doctorImgList = doctorImgDao.queryDoctorImgList(doctorId);
        //删除原来的图片
        for(DoctorImg doctorImg:doctorImgList) {
            ImageUtil.deleteFileOrPath(doctorImg.getImgAddr());
        }
    }

    @Override
    public DoctorExecution getDoctorList(Doctor doctorCondition, int pageIndex, int pageSize) {
        //页码转换成数据库的行码，并调用dao 层取回指定页码的医生列表
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex,pageSize);
        List<Doctor> doctorList = doctorDao.queryDoctorList(doctorCondition,rowIndex,pageSize);
        //基于同样的查询条件返回查询条件下的医生总数
        int count = doctorDao.queryDoctorCount(doctorCondition);
        DoctorExecution de = new DoctorExecution();
        de.setDoctorList(doctorList);
        de.setCount(count);
        return de;
    }

    private void addDoctorImgList(Doctor doctor, List<ImageHolder> doctorImgList) {
        //获取图片路径，这里直接存放到某个文件夹下
        String dest = PathUtil.getHospitalImagePath(doctor.getHospital().getHospitalId());
        List<DoctorImg> doctorImgList1 = new ArrayList<DoctorImg>();
        for(ImageHolder doctorImageHolder:doctorImgList){
            String imgAddr = ImageUtil.generateNormalImg(doctorImageHolder,dest);
            DoctorImg doctorImg = new DoctorImg();
            doctorImg.setImgAddr(imgAddr);
            doctorImg.setDoctorId(doctor.getDoctorId());
            doctorImg.setCreateTime(new Date());
            doctorImgList1.add(doctorImg);
        }
        if(doctorImgList1.size()>0) {
            try{
                int effectedNum = doctorImgDao.batchInsertDoctorImg(doctorImgList1);
                if(effectedNum<=0) {
                    throw new DoctorOperationException("创建医生详情图片失败");
                }
            }catch (Exception e) {
                throw new DoctorOperationException("创建医生详情图片失败"+ e.toString());
            }
        }
    }

    private void addThumbnail(Doctor doctor, ImageHolder thumbnail) {
        String dest = PathUtil.getHospitalImagePath(doctor.getHospital().getHospitalId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail,dest);
        doctor.setImgAddr(thumbnailAddr);
    }
}
