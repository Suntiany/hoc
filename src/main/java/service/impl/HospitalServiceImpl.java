package service.impl;

import dao.HospitalDao;
import dto.HospitalExecution;
import dto.ImageHolder;
import entity.Hospital;
import enums.HospitalStateEnum;
import exceptions.HospitalOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.HospitalService;
import util.ImageUtil;
import util.PageCalculator;
import util.PathUtil;

import java.awt.geom.Path2D;
import java.util.Date;
import java.util.List;

@Service
public class HospitalServiceImpl implements HospitalService {
    @Autowired
    private HospitalDao hospitalDao;

    @Override
    @Transactional
    public HospitalExecution addHospital(Hospital hospital, ImageHolder thumbnail) {
        //空值判断
        if(hospital==null){
            return new HospitalExecution(HospitalStateEnum.NULL_HOSPITAL);
        }
        try{
            //给医院信息赋予初始值
            hospital.setEnableStatus(0);
            hospital.setCreateTime(new Date());
            hospital.setLastEditTime(new Date());
            hospital.setPriority(1);
            //添加医院信息
            int effectedNum = hospitalDao.insertHospital(hospital);
            if(effectedNum<=0){
                throw new HospitalOperationException("医院创建失败");
            }else{
                if(thumbnail.getImage()!=null){
                    //存储图片
                    try{
                        addHospitalImg(hospital,thumbnail);
                    }catch (Exception e){
                        throw new HospitalOperationException("addHospital error" + e.getMessage());
                    }
                    //更新医院的图片地址
                    effectedNum = hospitalDao.updateHospital(hospital);
                    if(effectedNum<=0){
                        throw new HospitalOperationException("更新图片地址失败");
                    }
                }
            }
        }catch (Exception e){
            throw new HospitalOperationException("addHospital error" + e.getMessage());
        }
        return new HospitalExecution(HospitalStateEnum.CHECK,hospital);
    }

    @Override
    public Hospital getByHospitalId(long hospitalId) {
        return hospitalDao.queryByHospitalId(hospitalId);
    }

    private void addHospitalImg(Hospital hospital, ImageHolder thumbnail) {
        //获取hospital图片目录的相对地址
        String dest = PathUtil.getHospitalImagePath(hospital.getHospitalId());
        String hospitalImgAddr = ImageUtil.generateThumbnail(thumbnail,dest);
        hospital.setHospitalImg(hospitalImgAddr);
    }

    @Override
    public HospitalExecution modifyHospital(Hospital hospital, ImageHolder thumbnail) throws HospitalOperationException {
        if(hospital==null||hospital.getHospitalId()==null){
            return new HospitalExecution(HospitalStateEnum.NULL_HOSPITAL);
        }else{
            try{
                //1.判断是否需要处理图片
                if(thumbnail!=null&&thumbnail.getImage()!=null && thumbnail.getImageName()!=null && !"".equals(thumbnail.getImageName())) {
                    Hospital tempHospital = hospitalDao.queryByHospitalId(hospital.getHospitalId());
                    if(tempHospital.getHospitalImg()!=null) {
                        ImageUtil.deleteFileOrPath(tempHospital.getHospitalImg());
                    }
                    addHospitalImg(hospital,thumbnail);
                }
                //2.更新医疗机构信息
                hospital.setLastEditTime(new Date());
                int effectedNum = hospitalDao.updateHospital(hospital);
                if(effectedNum<=0){
                    return new HospitalExecution(HospitalStateEnum.INNER_ERROR);
                }else{
                    hospital = hospitalDao.queryByHospitalId(hospital.getHospitalId());
                    return new HospitalExecution(HospitalStateEnum.SUCCESS,hospital);
                }
            }catch (Exception e){
                throw new HospitalOperationException("modifyHospital error" + e.getMessage());
            }
        }
    }

    @Override
    public HospitalExecution getHospitalList(Hospital hospitalCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex,pageSize);
        List<Hospital> hospitalList = hospitalDao.queryHospitalList(hospitalCondition,rowIndex,pageSize);
        int count = hospitalDao.queryHospitalCount(hospitalCondition);
        HospitalExecution he = new HospitalExecution();
        if(hospitalList!=null){
            he.setHospitalList(hospitalList);
            he.setCount(count);
        }else{
            he.setState(HospitalStateEnum.INNER_ERROR.getState());
        }
        return he;
    }

    @Override
    public HospitalExecution getAllHospitalList(Hospital hospitalCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex,pageSize);
        hospitalCondition.setEnableStatus(1);
        List<Hospital> hospitalList = hospitalDao.allHospitalList(hospitalCondition,rowIndex,pageSize);
        HospitalExecution he = new HospitalExecution();
        if(hospitalList!=null){
            he.setHospitalList(hospitalList);
        }else{
            he.setState(HospitalStateEnum.INNER_ERROR.getState());
        }
        return he;
    }
}
