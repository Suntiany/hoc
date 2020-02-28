package service;

import dto.DoctorCategoryExecution;
import entity.DoctorCategory;
import exceptions.DoctorCategoryOperationException;

import java.util.List;

public interface DoctorCategoryService {
    /**
     * 查询指定某所医院下的所有科室类别信息
     */
    List<DoctorCategory> getDoctorCategoryList(long hospitalId);

    /**
     * 批量添加科室
     * @param doctorCategoryList
     * @return
     * @throws DoctorCategoryOperationException
     */
    DoctorCategoryExecution batchAddDoctorCategory(List<DoctorCategory> doctorCategoryList) throws DoctorCategoryOperationException;

    /**
     * 将此类别下的医生的类别id置为空，再删除掉该商品类别
     */
    DoctorCategoryExecution deleteDoctorCategory(long doctorCategoryId,long hospitalId) throws DoctorCategoryOperationException;
}
