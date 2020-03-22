package web.doctor;

import dto.UserExecution;
import entity.Consultation;
import enums.UserStateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.ConsultationService;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value="/doctor")
public class DoctorAdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private ConsultationService consultationService;

    /**
     * 医生端用户列表用来请求获得用户数据的URL
     * @param request
     * @return
     */
    @RequestMapping(value = "/getuserlist",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> addDoctor(HttpServletRequest request) {
        //接收医生id 用来获取用户列表
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //暂时做一个固定的医生id 做好登录功能后再改
        Long doctorId = 4L;
        try{
        UserExecution ue = userService.getByDoctorId(doctorId);
        if(ue.getState() ==UserStateEnum.SUCCESS.getState()){
            modelMap.put("success",true);
            modelMap.put("userList",ue.getUserList());
            modelMap.put("doctor",ue.getUserList().get(0).getDoctor());
        }else{
            modelMap.put("success",false);
        }
    }catch(Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        return modelMap;
    }

    /**
     * 医生通过ConsultId来获取问诊单信息
     */
    @RequestMapping(value = "/getconsultbyconsultid",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getConsultInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long consultId = Long.parseLong(request.getParameter("consultId"));
        try {
            Consultation consultation = consultationService.getConsultByConsultId(consultId);
            if(consultation!=null){
                modelMap.put("success",true);
                modelMap.put("consultation",consultation);
            }else{
                modelMap.put("success",false);
            }
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        return modelMap;
    }


    /**
     * 转发请求到WEB_INF 里面的/doctor/userlist.html
     */
    @RequestMapping(value="/userlist")
    public String shopList(){
        return "doctor/userlist";
    }

    /**
     * 转发URL请求到WEB_INF 里面的/doctor/handleuser.html
     */
    @RequestMapping(value="/handleuser")
    public String handleuser(){
        return "doctor/handleuser";
    }

    /**
     * 转发URl请求到WEB_INF 里面的/doctor/userinfo.html
     */
    @RequestMapping(value="/userinfo")
    public String getuserinfo(){
        return "doctor/userinfo";
    }

    /**
     * 转发URL请求到WEB_INF 里面的/doctor/userstatus.html
     */
    @RequestMapping(value="/userstatus")
    public String getuserstatus(){
        return "doctor/userstatus";
    }

    /**
     * 转发URL请求到WEB_INF 里面的/doctor/userconsult.html
     */
    @RequestMapping(value="/userconsult")
    public String getuserconsult(){
        return "doctor/userconsult";
    }

    /**
     * 转发请求到 WEB_INF 里面的/doctor/dealconsult.html
     */
    @RequestMapping(value="/dealconsult")
    public String dealconsult(){
        return "doctor/dealconsult";
    }
}
