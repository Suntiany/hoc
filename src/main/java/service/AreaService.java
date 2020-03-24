package service;

import dto.AreaExecution;
import entity.Area;

import java.util.List;

public interface AreaService {
    List<Area> getAreaList();
    /**
     * 增加区域信息
     *
     * @param area
     * @return
     */
    AreaExecution addArea(Area area);

    /**
     * 修改区域信息
     *
     * @param area
     * @return
     */
    AreaExecution modifyArea(Area area);

    /**
     * 删除Area信息
     * @param areaId
     * @return
     */
    AreaExecution deleteArea(long areaId);
}
