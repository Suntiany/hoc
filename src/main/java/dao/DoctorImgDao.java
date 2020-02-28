package dao;

import entity.DoctorImg;

import java.util.List;

public interface DoctorImgDao {
    /**
     * 批量添加医生详情图片
     * @param doctorImgList
     * @return
     */
    int batchInsertDoctorImg(List<DoctorImg> doctorImgList);

    /**
     * 删除指定医生下的所有详情图
     * @param doctorId
     * @return
     */
    int deleteDoctorImgByDoctorId(long doctorId);

    /**
     * 列出某个商品的详情图列表
     * @param doctorId
     * @return
     */
    List<DoctorImg> queryDoctorImgList(long doctorId);
}
