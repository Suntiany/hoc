package web.doctor;

import dto.UserExecution;
import enums.UserStateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value="/doctor")
public class DoctorAdminController {
    @Autowired
    private UserService userService;
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
        }else{
            modelMap.put("success",false);
        }
    }catch(Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        }
        return modelMap;
    }
}
