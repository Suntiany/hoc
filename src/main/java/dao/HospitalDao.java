package dao;

import entity.Hospital;

public interface HospitalDao {
    /**
     * 区域负责人新增一所医疗机构
     * @param hospital
     * @return
     */
    int insertHospital(Hospital hospital);
}
