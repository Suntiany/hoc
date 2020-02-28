package web.hospitaladmin;

import dto.DoctorCategoryExecution;
import dto.Result;
import entity.DoctorCategory;
import entity.Hospital;
import enums.DoctorCategoryStateEnum;
import exceptions.DoctorCategoryOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.annotation.RequestScope;
import service.DoctorCategoryService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("hospitaladmin")
public class DoctorCategoryManagementController {
    @Autowired
    private DoctorCategoryService doctorCategoryService;

    @RequestMapping(value="/getdoctorcategory",method= RequestMethod.GET)
    @ResponseBody
    private Result<List<DoctorCategory>> getDoctorCategoryList(HttpServletRequest request) {
        Hospital currentHospital = (Hospital) request.getSession().getAttribute("currentHospital");
        List<DoctorCategory> list = null;
        if(currentHospital!=null && currentHospital.getHospitalId()>0){
            list = doctorCategoryService.getDoctorCategoryList(currentHospital.getHospitalId());
            return new Result<List<DoctorCategory>>(true,list);
        }
        return null;
    }

    @RequestMapping(value="/adddoctorcategorys",method=RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> addDoctorCategorys(@RequestBody List<DoctorCategory> doctorCategoryList,HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        Hospital currentHospital = (Hospital)request.getSession().getAttribute("currentHospital");
        for(DoctorCategory dc:doctorCategoryList) {
            dc.setHospitalId(currentHospital.getHospitalId());
        }
        if(doctorCategoryList!=null && doctorCategoryList.size()>0) {
            try{
                DoctorCategoryExecution de = doctorCategoryService.batchAddDoctorCategory(doctorCategoryList);
                if(de.getState()== DoctorCategoryStateEnum.SUCCESS.getState()) {
                    modelMap.put("success",true);
                }else{
                    modelMap.put("success",false);
                    modelMap.put("errMsg",de.getStateInfo());
                }
            }catch (DoctorCategoryOperationException e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.toString());
                return modelMap;
            }
        }else{
            modelMap.put("success", false);
            modelMap.put("errMsg", "请至少输入一个科室类别");
        }
        return modelMap;
    }

    @RequestMapping(value="/removedoctorcategory",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> removeDoctorCategory(Long doctorCategoryId,HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<String, Object>();
        if(doctorCategoryId!=null&&doctorCategoryId>0) {
            try{
                Hospital currentHospital = (Hospital) request.getSession().getAttribute("currentHospital");
                DoctorCategoryExecution de = doctorCategoryService.deleteDoctorCategory(doctorCategoryId,currentHospital.getHospitalId());
                if(de.getState()==DoctorCategoryStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }else{
                    modelMap.put("success", false);
                    modelMap.put("errMsg", de.getStateInfo());
                }
            }catch (DoctorCategoryOperationException e){
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请至少选择一个科室类别");
        }
        return modelMap;
    }
}
