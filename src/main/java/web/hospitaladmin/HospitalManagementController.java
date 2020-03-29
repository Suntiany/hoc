package web.hospitaladmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.HospitalExecution;
import dto.ImageHolder;
import entity.Area;
import entity.Hospital;
import enums.HospitalStateEnum;
import exceptions.HospitalOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import service.AreaService;
import service.HospitalService;
import util.CodeUtil;
import util.HttpServletRequestUtil;
import util.ImageUtil;
import util.PathUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/hospitaladmin")
public class HospitalManagementController {
    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private AreaService areaService;

    @RequestMapping(value="/gethospitalmanagementinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getHospitalManagementInfo(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<String, Object>();
        long hospitalId = HttpServletRequestUtil.getLong(request,"hospitalId");
        if(hospitalId<=0){
            Object currentHospitalObj = request.getSession().getAttribute("currentHospital");
            if(currentHospitalObj==null){
                modelMap.put("redirect",true);
                modelMap.put("url","/hoc/hospitaladmin/hospitallist");
            }else{
                Hospital currentHospital = (Hospital) currentHospitalObj;
                modelMap.put("redirect",false);
                modelMap.put("hospitalId",currentHospital.getHospitalId());
            }
        }else{
            Hospital currentHospital = new Hospital();
            currentHospital.setHospitalId(hospitalId);
            request.getSession().setAttribute("currentHospital",currentHospital);
            modelMap.put("success",true);
            modelMap.put("redirect",false);
        }
        return modelMap;
    }

    @RequestMapping(value="/gethospitallist",method=RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getHospitalList(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<String, Object>();
        Integer areaId = (Integer) request.getSession().getAttribute("areaId");
        Hospital hospitalCondition = new Hospital();
        Area area = new Area();
        area.setAreaId(areaId);
        hospitalCondition.setArea(area);
        try{
            HospitalExecution he = hospitalService.getHospitalList(hospitalCondition,0,100);
            modelMap.put("hospitalList",he.getHospitalList());
            request.getSession().setAttribute("hospitalList",he.getHospitalList());
            modelMap.put("user",area.getAreaId()+"号区域管理者");
            modelMap.put("success",true);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value="/gethospitalinitinfo",method=RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getHospitalinitInfo(){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        List<Area> areaList = new ArrayList<Area>();
        try{
            areaList = areaService.getAreaList();
            modelMap.put("areaList",areaList);
            modelMap.put("success",true);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value="/registerhospital",method= RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> registerHospital(HttpServletRequest request){
        //1接收并转化相应的参数，包括医院信息以及图片信息
        Map<String,Object> modelMap = new HashMap<String, Object>();
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码");
            return modelMap;
        }
        String hospitalStr = HttpServletRequestUtil.getString(request,"hospitalStr");
        ObjectMapper mapper = new ObjectMapper();
        Hospital hospital = null;
        try{
            hospital = mapper.readValue(hospitalStr,Hospital.class);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        CommonsMultipartFile hospitalImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if(commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
            hospitalImg = (CommonsMultipartFile)multipartHttpServletRequest.getFile("hospitalImg");
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","上传图片不能为空");
            return modelMap;
        }
        //注册医院
        if(hospital!=null&&hospitalImg!=null&&hospital.getOwnerName()!=null){
            File hospitalImgFile = new File(PathUtil.getImgBasePath()+ ImageUtil.getRandomFileName());
            try{
                hospitalImgFile.createNewFile();
            }catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
                return modelMap;
            }
            HospitalExecution he = null;
            try{
                ImageHolder imageHolder = new ImageHolder(hospitalImg.getOriginalFilename(),hospitalImg.getInputStream());
                he = hospitalService.addHospital(hospital,imageHolder);
                if(he.getState()== HospitalStateEnum.CHECK.getState()){
                    modelMap.put("success",true);
                    //该用户可以操作的列表
                    List<Hospital> hospitalList = (List<Hospital>)request.getSession().getAttribute("hospitalList");
                    if(hospitalList==null||hospitalList.size()==0){
                        hospitalList = new ArrayList<Hospital>();
                    }
                    hospitalList.add(he.getHospital());
                    request.getSession().setAttribute("hospitalList",hospitalList);
                }else{
                    modelMap.put("success",false);
                    modelMap.put("errMsg",he.getStateInfo());
                }
            }catch (IOException e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
            }
            return modelMap;
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入医院信息（院长信息必填）");
            return modelMap;
        }
    }

    @RequestMapping(value="/gethospitalbyid",method=RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getHospitalById(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        Long hospitalId = HttpServletRequestUtil.getLong(request,"hospitalId");
        //Hospital currentHospital = (Hospital)request.getSession().getAttribute("currentHospital");
        //long hospitalId = currentHospital.getHospitalId();
        if(hospitalId>-1){
            try{
                Hospital hospital = hospitalService.getByHospitalId(hospitalId);
                List<Area> areaList = areaService.getAreaList();
                modelMap.put("hospital",hospital);
                modelMap.put("areaList",areaList);
                modelMap.put("success",true);
            }catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg","empty hospitalId");
            }
        }
        return modelMap;
    }

    @RequestMapping(value="/modifyhospital",method=RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> modifyhospital(HttpServletRequest request) {
        //1接收并转化相应的参数，包括医院信息以及图片信息
        Map<String,Object> modelMap = new HashMap<String, Object>();
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码");
            return modelMap;
        }
        String hospitalStr = HttpServletRequestUtil.getString(request,"hospitalStr");
        ObjectMapper mapper = new ObjectMapper();
        Hospital hospital = null;
        try{
            hospital = mapper.readValue(hospitalStr,Hospital.class);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        CommonsMultipartFile hospitalImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if(commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
            hospitalImg = (CommonsMultipartFile)multipartHttpServletRequest.getFile("hospitalImg");
        }
        // 2.修改医院信息
        if(hospital!=null&&hospital.getHospitalId()!=null) {
            HospitalExecution he;
            try{
                if(hospitalImg==null){
                    he = hospitalService.modifyHospital(hospital,null);
                }else{
                    ImageHolder imageHolder = new ImageHolder(hospitalImg.getOriginalFilename(),hospitalImg.getInputStream());
                    he = hospitalService.modifyHospital(hospital,imageHolder);
                }
                if(he.getState()==HospitalStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }else{
                    modelMap.put("success",false);
                    modelMap.put("errMsg",he.getStateInfo());
                }
            }catch (IOException e){
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }catch (HospitalOperationException e){
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
            return modelMap;
        }else{
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入医院Id");
            return modelMap;
        }
    }
}
