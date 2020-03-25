package dao;

import entity.Hospital;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HospitalDao {
    /**
     * 区域负责人新增一所医疗机构
     * @param hospital
     * @return
     */
    int insertHospital(Hospital hospital);


    /**
     * 区域负责人更新某所医疗机构的信息
     * @param hospital
     * @return
     */
    int updateHospital(Hospital hospital);

    /**
     * 通过hospitalId来查询医院的信息
     * @param hospitalId
     * @return
     */
    Hospital queryByHospitalId(long hospitalId);

    /**
     * 分页查询医院，通过areaID这个前提来获取本区域下医院名字或者医院状态的列表
     * @param hospital
     * @param rowIndex 从第几行开始获取数据
     * @param pageSize 返回的条数有几条
     * @return
     */
    List<Hospital> queryHospitalList(@Param("hospitalCondition") Hospital hospital,@Param("rowIndex") int rowIndex,@Param("pageSize") int pageSize);

    /**
     * 获取满足查询条件的医院的个数
     * @param hospitalCondition
     * @return
     */
    int queryHospitalCount(@Param("hospitalCondition") Hospital hospitalCondition);

    /**
     * 获取数据库中所有医院的列表只有审核通过的
     * @param hospital
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Hospital> allHospitalList(@Param("hospitalCondition") Hospital hospital,@Param("rowIndex") int rowIndex,@Param("pageSize") int pageSize);


    /**
     * 超级管理员获取所有医院列表包括未审核的
     */
    List<Hospital> superAllHospitalList(@Param("hospitalCondition") Hospital hospital,@Param("rowIndex") int rowIndex,@Param("pageSize") int pageSize);
}

