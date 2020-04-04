package web.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.ImageHolder;
import entity.Consultation;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import service.ConsultationService;
import service.UserService;
import util.CodeUtil;
import util.HttpServletRequestUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class ConsulationController {
    @Autowired
    private ConsultationService consultationService;
    @Autowired
    private UserService userService;

    @RequestMapping(value="/addconsultbysession",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> add(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码");
            return modelMap;
        }
        ImageHolder thumbnail = null;
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        try{
            if(multipartResolver.isMultipart(request)) {
                thumbnail = handleImage(request,thumbnail);
            }else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为空");
                return modelMap;
            }
        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        String symptom = HttpServletRequestUtil.getString(request,"consultInfo");
        Long userId = (Long)request.getSession().getAttribute("userId");
        User user  = userService.getByUserId(userId);
        Long doctorId = user.getDoctor().getDoctorId();
        Consultation consultation = new Consultation();
        consultation.setStatus("未查阅");
        consultation.setComment("未查阅");
        consultation.setLastEditTime(new Date());
        consultation.setCreateTime(new Date());
        consultation.setDoctorId(doctorId);
        consultation.setUserId(userId);
        consultation.setSymptom(symptom);
        int effectedNum = consultationService.insert(consultation,thumbnail);
        if(effectedNum<0){
            modelMap.put("success",false);
        }else{
            modelMap.put("success",true);
        }
        return modelMap;
    }

    @RequestMapping(value="/getconsultbyuser",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> selectbyUserId(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Long userId = (Long)request.getSession().getAttribute("userId");
        List<Consultation> consultationList = consultationService.getConsultByUserId(userId);
        if(consultationList.size()>0){
            modelMap.put("success",true);
            modelMap.put("consultationList",consultationList);
        }else{
            modelMap.put("success",false);
        }
        return modelMap;
    }

    @RequestMapping(value="/getconsultbydoctor",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> selectbyDoctorId(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<String, Object>();
        long userId = Long.parseLong(request.getParameter("userId"));
        long doctorId = 4L;
        List<Consultation> consultationList = consultationService.getConsultByDoctorId(doctorId,userId);
        if(consultationList.size()>0){
            modelMap.put("success",true);
            modelMap.put("consultationList",consultationList);
        }else {
            modelMap.put("success",false);
        }
        return modelMap;
    }

    @RequestMapping(value="/dealconsultbyconsultid",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> updateConsultation(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<String, Object>();
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码");
            return modelMap;
        }
        int consultId = Integer.parseInt(request.getParameter("consultId"));
        String comment = HttpServletRequestUtil.getString(request,"comment");
        Consultation consultation = new Consultation();
        try{
            consultation.setConsultId(consultId);
            consultation.setComment(comment);
            consultation.setStatus("已建议");
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        int effectedNum = consultationService.update(consultation);
        if(effectedNum>0){
            modelMap.put("success",true);
        }else{
            modelMap.put("success",false);
        }
        return modelMap;
    }

    private ImageHolder handleImage(HttpServletRequest request, ImageHolder thumbnail) throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 取出缩略图并构建ImageHolder对象
        CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
        if (thumbnailFile != null) {
            thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
        }
        return thumbnail;
    }
}
