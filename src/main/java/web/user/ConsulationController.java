package web.user;

import entity.Consultation;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.ConsultationService;
import service.UserService;
import util.CodeUtil;
import util.HttpServletRequestUtil;

import javax.servlet.http.HttpServletRequest;
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
        int effectedNum = consultationService.insert(consultation);
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
}
