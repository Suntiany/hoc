package service;

import dto.DoctorExecution;
import dto.ImageHolder;
import entity.Doctor;
import entity.DoctorAuth;
import exceptions.DoctorOperationException;

import java.util.List;

public interface DoctorService {
    /**
     * 添加一位医生的信息
     * @param doctor
     * @param thumbnail
     * @param doctorImgList
     * @return
     * @throws DoctorOperationException
     */
    DoctorExecution addDoctor(Doctor doctor, ImageHolder thumbnail, List<ImageHolder> doctorImgList) throws DoctorOperationException;


    /**
     * 通过doctorId查询唯一的医生信息
     * @param doctorId
     * @return
     */
    Doctor getDoctorById(long doctorId);


    /**
     * 修改医生信息及图片处理
     * @param doctor
     * @param thumbnail
     * @param doctorImgHolderList
     * @return
     * @throws DoctorOperationException
     */
    DoctorExecution modifyDoctor(Doctor doctor,ImageHolder thumbnail,List<ImageHolder> doctorImgHolderList) throws DoctorOperationException;


    /**
     * 查询医生列表并分页，可输入的条件有:医生名（模糊），医生状态，医院id,科室类别
     * @param doctorCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    DoctorExecution getDoctorList(Doctor doctorCondition,int pageIndex,int pageSize);


    /**
     * 新增一条医生的账户信息 用于医生登录医生系统
     */
    DoctorExecution addDoctorAccount(DoctorAuth doctorAuth);
}
