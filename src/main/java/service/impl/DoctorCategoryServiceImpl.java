package service.impl;

import dao.DoctorCategoryDao;
import dao.DoctorDao;
import dto.DoctorCategoryExecution;
import entity.DoctorCategory;
import enums.DoctorCategoryStateEnum;
import exceptions.DoctorCategoryOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.DoctorCategoryService;

import java.util.List;

@Service
public class DoctorCategoryServiceImpl implements DoctorCategoryService {
    @Autowired
    private DoctorCategoryDao doctorCategoryDao;
    @Autowired
    private DoctorDao doctorDao;
    @Override
    public List<DoctorCategory> getDoctorCategoryList(long hospitalId) {
        return doctorCategoryDao.queryDoctorCategoryList(hospitalId);
    }

    @Override
    public DoctorCategoryExecution batchAddDoctorCategory(List<DoctorCategory> doctorCategoryList) throws DoctorCategoryOperationException {
        if(doctorCategoryList!=null && doctorCategoryList.size()>0){
            try{
                int effectedNum = doctorCategoryDao.batchInsertDoctorCategory(doctorCategoryList);
                if(effectedNum<=0){
                    throw new DoctorCategoryOperationException("科室类别创建失败");
                }else{
                    return new DoctorCategoryExecution(DoctorCategoryStateEnum.SUCCESS);
                }
            }catch (Exception e){
                throw new DoctorCategoryOperationException("batchAddDoctorCategory error:"+e.getMessage());
            }
        }else{
            return new DoctorCategoryExecution(DoctorCategoryStateEnum.EMPTY_LIST);
        }
    }

    @Override
    public DoctorCategoryExecution deleteDoctorCategory(long doctorCategoryId, long hospitalId) throws DoctorCategoryOperationException {
        //1.解除tb_doctor里的医生与科室之间的关联
        try{
            int effectedNum = doctorDao.updateDoctorCategoryToNull(doctorCategoryId);
            if(effectedNum<0) {
                throw new DoctorCategoryOperationException("科室类别更新失败");
            }
        }catch (Exception e) {
            throw new DoctorCategoryOperationException("deleteDoctorCategory error" + e.getMessage());
        }
        //2.删除该productCategory
        try{
            int effectedNum = doctorCategoryDao.deleteDoctorCategory(doctorCategoryId,hospitalId);
            if(effectedNum<=0){
                throw new DoctorCategoryOperationException("科室删除失败");
            }else{
                return new DoctorCategoryExecution(DoctorCategoryStateEnum.SUCCESS);
            }
        }catch (Exception e){
            throw new DoctorCategoryOperationException("deleteDoctorCategory error"+e.getMessage());
        }
    }
}
