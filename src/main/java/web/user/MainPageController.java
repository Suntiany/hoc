package web.user;

import dto.HospitalExecution;
import entity.Area;
import entity.HeadLine;
import entity.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.AreaService;
import service.HeadLineService;
import service.HospitalService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class MainPageController {
    @Autowired
    private HeadLineService headLineService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private HospitalService hospitalService;

    /**
     * 初始化用户系统的签约页面信息，包括获取与用户相同区域的医院列表，所有医院列表，以及头条列表
     */
    @RequestMapping(value="/signpageinfo",method= RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> signPageInfo() {
        Map<String,Object> modelMap = new HashMap<String, Object>();
        Area area = new Area();
        area.setAreaId(1);
        Hospital hospitalCondition = new Hospital();
        hospitalCondition.setArea(area);
        Hospital allHospital = new Hospital();
        try{
            HospitalExecution he = hospitalService.getHospitalList(hospitalCondition,0,100);
            HospitalExecution allhe = hospitalService.getAllHospitalList(allHospital,0,100);
            modelMap.put("hospitalListArea",he.getHospitalList());
            modelMap.put("hospitalListAll",allhe.getHospitalList());
        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        List<HeadLine> headLineList = new ArrayList<HeadLine>();
        try{
            //获取状态为可用（1）的头条列表
            HeadLine headLineCondition = new HeadLine();
            headLineCondition.setEnableStatus(1);
            headLineList = headLineService.getHeadLineList(headLineCondition);
            modelMap.put("headLineList", headLineList);
        }catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        modelMap.put("success", true);
        return modelMap;
    }
}
