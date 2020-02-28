package dao;

import entity.DoctorCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DoctorCategoryDao {
    /**
     * 通过hospitalId 查询医院拥有的科室类别
     */
    List<DoctorCategory> queryDoctorCategoryList(long hospitalId);

    /**
     * 科室批量添加
     * 返回一个int值 表明添加了多少个
     */
    int batchInsertDoctorCategory(List<DoctorCategory> doctorCategoryList);

    /**
     * 删除指定的科室
     */
    int deleteDoctorCategory(@Param("doctorCategoryId") long doctorCategoryId,@Param("hospitalId") long hospitalId);
}
