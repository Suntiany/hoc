package dao;

import entity.Area;

import java.util.List;

public interface AreaDao {
    /*
    列出区域列表
 */
    List<Area> queryArea();

    /**
     * 新增一个区域
     */
    int insertArea(Area area);

    /**
     * 更新区域信息
     */
    int updateArea(Area area);

    /**
     * 删除区域信息
     *
     * @param areaId
     * @return
     */
    int deleteArea(long areaId);

}
