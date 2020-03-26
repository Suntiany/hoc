package web.superadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.HospitalExecution;
import dto.UserExecution;
import entity.Area;
import entity.Hospital;
import entity.User;
import enums.HospitalStateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/superadmin")
public class SuUserController {
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/listuser",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listUser(){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        UserExecution ue = null;
        try {
            ue = userService.getAllUserList();
            modelMap.put("rows",ue.getUserList());
            modelMap.put("total",ue.getCount());
        }catch (Exception e){
            e.printStackTrace();
            modelMap.put("success",false);
            modelMap.put("errM",e.toString());
        }
        return modelMap;
    }

    /**
     * 修改用户信息 主要是修改用户账号的权限
     * @param userStr
     * @param request
     * @return
     */
    @RequestMapping(value = "/modifyuser", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyHospital(String userStr, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        User  user = null;
        try {
            user = mapper.readValue(userStr, User.class);
            user.setUserName((user.getUserName()==null)?null: URLDecoder.decode(user.getUserName(),"UTF-8"));
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        // 空值判断
        if ( user.getUserId()>0) {
            try {
                // 修改用户
                UserExecution ue = userService.suModify(user);
                if (ue.getState() == HospitalStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", ue.getStateInfo());
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
