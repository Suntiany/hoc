package web.hospitaladmin;

import dto.DoctorCategoryExecution;
import dto.DoctorExecution;
import dto.Result;
import entity.Doctor;
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
import service.DoctorService;
import util.HttpServletRequestUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("hospitaladmin")
public class DoctorCategoryManagementController {
    @Autowired
    private DoctorCategoryService doctorCategoryService;
    @Autowired
    private DoctorService doctorService;

    /**
     * 获取医院id下该医院的所有医生列表
     * @param request
     * @return
     */
    @RequestMapping(value="/getdoctorlistbyhospital",method=RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getDoctorListByHospital(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<String, Object>();
        //获取前台传过来的页码
        int pageIndex = HttpServletRequestUtil.getInt(request,"pageIndex");
        //获取前台传古来的每页要求返回的商品数上限
        int pageSize = HttpServletRequestUtil.getInt(request,"pageSize");
        //从session中获取医院信息，主要是获取hospitalId
        Hospital currentHospital = (Hospital) request.getSession().getAttribute("currentHospital");
        //空值判断
        if((pageIndex>-1) && (pageSize>-1) &&(currentHospital!=null) &&(currentHospital.getHospitalId()!=null)) {
            //获取传入的需要检索的条件，包括是否需要从某个科室类别以及模糊查找医生名去帅选某个医院下的医生列表
            //筛选的条件可以进行排列组合
            long doctorCategoryId = HttpServletRequestUtil.getLong(request,"doctorCategoryId");
            String doctorName = HttpServletRequestUtil.getString(request,"doctorName");
            Doctor doctorCondition = compactDoctorCondition(currentHospital.getHospitalId(),doctorCategoryId,doctorName);
            //传入查询条件以及分页信息进行查询，返回相应商品列表以及总数
            DoctorExecution de = doctorService.getDoctorList(doctorCondition,pageIndex,pageSize);
            modelMap.put("doctorList",de.getDoctorList());
            modelMap.put("count",de.getCount());
            modelMap.put("success",true);
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","empty pageSize or pageIndex or HospitalId");
        }
        return modelMap;
    }

    private Doctor compactDoctorCondition(Long hospitalId, long doctorCategoryId, String doctorName) {
        Doctor doctorCondition = new Doctor();
        Hospital hospital = new Hospital();
        hospital.setHospitalId(hospitalId);
        doctorCondition.setHospital(hospital);
        //若有指定类别的要求则添加进去
        if(doctorCategoryId !=-1) {
            DoctorCategory doctorCategory = new DoctorCategory();
            doctorCategory.setDoctorCategoryId(doctorCategoryId);
            doctorCondition.setDoctorCategory(doctorCategory);
        }
        //如有医生名模糊查询的要求则添加进去
        if(doctorName!=null) {
            doctorCondition.setDoctorName(doctorName);
        }
        return doctorCondition;
    }

    @RequestMapping(value="/getdoctorcategorylist",method= RequestMethod.GET)
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
            dc.setCreateTime(new Date());
            dc.setLastEditTime(new Date());
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
