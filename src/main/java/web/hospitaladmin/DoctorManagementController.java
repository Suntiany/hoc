package web.hospitaladmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.DoctorExecution;
import dto.ImageHolder;
import entity.Doctor;
import entity.DoctorAuth;
import entity.DoctorCategory;
import entity.Hospital;
import enums.DoctorStateEnum;
import exceptions.DoctorOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import service.DoctorCategoryService;
import service.DoctorService;
import util.CodeUtil;
import util.HttpServletRequestUtil;

import javax.print.Doc;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/hospitaladmin")
public class DoctorManagementController {
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private DoctorCategoryService doctorCategoryService;

    //支持上传医生详情图的最大数量
    private static final int IMAGEMAXCOUNT = 6;

    @RequestMapping(value="/adddoctor",method= RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> addDoctor(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<String, Object>();
        //验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        //接受前端参数的变量的初始化，包括商品，缩略图，详情图列表实体类
        ObjectMapper mapper = new ObjectMapper();
        Doctor doctor = null;
        ImageHolder thumbnail = null;
        List<ImageHolder> doctorImgList = new ArrayList<ImageHolder>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        try {
            // 若请求中存在文件流，则取出相关的文件（包括缩略图和详情图）
            if (multipartResolver.isMultipart(request)) {
                thumbnail = handleImage(request, thumbnail, doctorImgList);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为空");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        try {
            String doctorStr = HttpServletRequestUtil.getString(request, "doctorStr");
            // 尝试获取前端传过来的表单string流并将其转换成Doctor实体类
            doctor = mapper.readValue(doctorStr, Doctor.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        if(doctor!=null && thumbnail!=null && doctorImgList.size()>0) {
            try{
                //从session中获取当前医院的id并赋值给doctor,减少对前端数据的依赖
                Hospital currentHospital = (Hospital)request.getSession().getAttribute("currentHospital");
                doctor.setHospital(currentHospital);
                //执行添加操作
                DoctorExecution de = doctorService.addDoctor(doctor,thumbnail,doctorImgList);
                if(de.getState()== DoctorStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }else{
                    modelMap.put("success",false);
                    modelMap.put("errMsg",de.getStateInfo());
                }
            }catch (DoctorOperationException e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.toString());
                return modelMap;
            }
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入医生信息");
        }
        return modelMap;
    }

    private ImageHolder handleImage(HttpServletRequest request, ImageHolder thumbnail, List<ImageHolder> doctorImgList) throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 取出缩略图并构建ImageHolder对象
        CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
        if (thumbnailFile != null) {
            thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
        }
        // 取出详情图列表并构建List<ImageHolder>列表对象，最多支持六张图片上传
        for (int i = 0; i < IMAGEMAXCOUNT; i++) {
            CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest.getFile("doctorImg" + i);
            if (productImgFile != null) {
                // 若取出的第i个详情图片文件流不为空，则将其加入详情图列表
                ImageHolder doctorImg = new ImageHolder(productImgFile.getOriginalFilename(),
                        productImgFile.getInputStream());
                doctorImgList.add(doctorImg);
            } else {
                // 若取出的第i个详情图片文件流为空，则终止循环
                break;
            }
        }
        return thumbnail;
    }


    /**
     * 通过医生id获取医生信息
     */
    @RequestMapping(value = "/getdoctorbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getDoctorById(@RequestParam Long doctorId) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (doctorId > -1){
            Doctor doctor = doctorService.getDoctorById(doctorId);
            List<DoctorCategory> doctorCategoryList = doctorCategoryService.getDoctorCategoryList(doctor.getHospital().getHospitalId());
            modelMap.put("doctor",doctor);
            modelMap.put("doctorCategoryList",doctorCategoryList);
            modelMap.put("success",true);
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","empty doctorId");
        }
        return modelMap;
    }


    /**
     * 修改医生信息
     */
    @RequestMapping(value = "/modifydoctor", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyDoctor(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //是医生信息编辑时候调用还是医生信息上下架时候调用
        //若为前者则进行验证码判断，后者则跳过验证码判断
        boolean statusChange = HttpServletRequestUtil.getBoolean(request,"statusChange");
        // 验证码判断
        if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        // 接收前端参数的变量的初始化，包括医生，缩略图，详情图列表实体类
        ObjectMapper mapper = new ObjectMapper();
        Doctor doctor = null;
        ImageHolder thumbnail = null;
        List<ImageHolder> doctorImgList = new ArrayList<ImageHolder>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        // 若请求中存在文件流，则取出相关的文件（包括缩略图和详情图）
        try {
            if (multipartResolver.isMultipart(request)) {
                thumbnail = handleImage(request, thumbnail, doctorImgList);
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        try {
            String doctorStr = HttpServletRequestUtil.getString(request, "doctorStr");
            // 尝试获取前端传过来的表单string流并将其转换成Doctor实体类
            doctor = mapper.readValue(doctorStr, Doctor.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        if(doctor!=null) {
            try{
                //从session中获取当前医院的id并赋值给doctor,减少对前端数据的依赖
                Hospital currentHospital = (Hospital)request.getSession().getAttribute("currentHospital");
                doctor.setHospital(currentHospital);
                //执行添加操作
                DoctorExecution de = doctorService.modifyDoctor(doctor,thumbnail,doctorImgList);
                if(de.getState()== DoctorStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }else{
                    modelMap.put("success",false);
                    modelMap.put("errMsg",de.getStateInfo());
                }
            }catch (DoctorOperationException e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.toString());
                return modelMap;
            }
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入医生信息");
        }
        return modelMap;
    }
}
