package dao;

import entity.Doctor;
import entity.User;
import org.apache.ibatis.annotations.Param;

import javax.print.Doc;
import javax.tools.DocumentationTool;
import java.util.List;

public interface DoctorDao {
    /**
     * 插入一条医生信息
     * @param doctor
     * @return
     */
    int insertDoctor(Doctor doctor);

    /**
     * 通过doctorId查询唯一的医生信息
     */
    Doctor queryDoctorById(long doctorId);

    /**
     * 更新医生信息
     */
    int updateDoctor(Doctor doctor);


    /**
     * 查询医生列表并分页，可输入的条件：医生名（模糊），医生状态，医院Id，科室类别
     */
    List<Doctor> queryDoctorList(@Param("doctorCondition") Doctor doctorCondition,@Param("rowIndex") int rowIndex,@Param("pageSize") int pageSize);


    /**
     * 查询对应的医生总数
     */
    int queryDoctorCount(@Param("doctorCondition") Doctor doctorCondition);

    /**
     * 删除医生类别之前，将医生类别ID 置为空
     */
    int updateDoctorCategoryToNull(long doctorCategoryId);

    /**
     * 删除医生信息
     */
    int deleteDoctor(@Param("doctorId") long doctorId,@Param("hospitalId") long hospitalId);

    /**
     * 通过医生名获取医生的ID
     * @param doctorName
     * @return
     */
    Doctor getDoctorIdByDoctorName(String doctorName);

    /**
     * 超级管理员获取医生列表
     * @return
     */
    List<Doctor> suGetDoctorList();

}
