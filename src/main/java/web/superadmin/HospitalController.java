package web.superadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.AreaExecution;
import dto.HospitalExecution;
import entity.Area;
import entity.HeadLine;
import entity.Hospital;
import entity.User;
import enums.AreaStateEnum;
import enums.HospitalStateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.HospitalService;
import util.HttpServletRequestUtil;
import web.hospitaladmin.HospitalAdminController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/superadmin")
public class HospitalController {
    @Autowired
    private HospitalService hospitalService;

    /**
     * 获取医院列表
     */
    @RequestMapping(value="/listhospital",method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShops(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<String, Object>();
        Hospital allHospital = new Hospital();
        try{
            HospitalExecution allhe = hospitalService.superGetAllHospitalList(allHospital,0,100);
            modelMap.put("rows",allhe.getHospitalList());
        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        modelMap.put("success", true);
        return modelMap;
    }

    /**
     * 修改医院信息 主要是审核区域管理员新建的医院
     * @param hospitalStr
     * @param request
     * @return
     */
    @RequestMapping(value = "/modifyhospital", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyHospital(String hospitalStr, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        Hospital hospital = null;
        try {
            // 接收前端传递过来的area json字符串信息并转换成Area实体类实例
            hospital = mapper.readValue(hospitalStr, Hospital.class);
            hospital.setHospitalName((hospital.getHospitalName()==null)?null:URLDecoder.decode(hospital.getHospitalName(),"UTF-8"));
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        // 空值判断
        if ( hospital.getHospitalId()>0) {
            try {
                // 修改区域信息
                HospitalExecution he = hospitalService.modifyHospital(hospital,null);
                if (he.getState() == HospitalStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", he.getStateInfo());
                }
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入区域信息");
        }
        return modelMap;
    }
}
