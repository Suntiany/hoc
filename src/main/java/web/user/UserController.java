package web.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.UserExecution;
import entity.Area;
import entity.User;
import entity.UserAuth;
import enums.UserStateEnum;
import exceptions.UserOperationExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.AreaService;
import service.UserAuthService;
import service.UserService;
import util.CodeUtil;
import util.HttpServletRequestUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/user",method= RequestMethod.GET)
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private AreaService areaService;
    @RequestMapping(value="/index",method=RequestMethod.GET)
    private String index() {
        return "user/index";
    }
    @RequestMapping(value="/newslist",method = RequestMethod.GET)
    private String news() {
        return "user/newslist";
    }
    @RequestMapping(value="/hospitalinfo",method=RequestMethod.GET)
    private String hospitalInfo() {
        return "user/hospitalinfo";
    }




    @RequestMapping(value="/getuserinitinfo",method=RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getUserinitInfo(){
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

    //用户信息的操作 这里是用户注册
    @RequestMapping(value="/registeruser",method=RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> registerUser(HttpServletRequest request){
        //接收相应的参数，包括用户信息以及用户的账号密码等
        Map<String,Object> modelMap = new HashMap<String, Object>();
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码");
            return modelMap;
        }
        String userStr = HttpServletRequestUtil.getString(request,"userStr");
        String userAuthStr = HttpServletRequestUtil.getString(request,"userAuthStr");
        ObjectMapper mapper = new ObjectMapper();
        User user = null;
        UserAuth userAuth = null;
        try{
            user = mapper.readValue(userStr,User.class);
            userAuth = mapper.readValue(userAuthStr,UserAuth.class);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        if(user!=null&&userAuth!=null){
            UserExecution ue = null;
            ue = userService.addUser(user,userAuth);
            if(ue.getState()== UserStateEnum.SUCCESS.getState()){
                modelMap.put("success",true);
                modelMap.put("user",ue.getUser());
            }
            return modelMap;
        }else{
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了空的用户信息");
            return modelMap;
        }
    }

    @RequestMapping(value="/getuserbysession",method=RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getUserById(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<String, Object>();
        Long userId = (Long)request.getSession().getAttribute("userId");
        if(userId>-1){
            try{
                User user = userService.getByUserId(userId);
                List<Area> areaList = areaService.getAreaList();
                UserAuth userAuth = userAuthService.getUserAuthByUserId(userId);
                modelMap.put("user",user);
                modelMap.put("areaList",areaList);
                modelMap.put("success",true);
                modelMap.put("userAuth",userAuth);
            }catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg","empty userId");
            }
        }
        return modelMap;
    }

    //用户信息的修改
    @RequestMapping(value="/modifyuser",method=RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> modifyUser(HttpServletRequest request) {
        //1接收并转化相应的参数，包括医院信息以及图片信息
        Map<String,Object> modelMap = new HashMap<String, Object>();
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码");
            return modelMap;
        }
        String userStr = HttpServletRequestUtil.getString(request,"userStr");
        String userAuthStr = HttpServletRequestUtil.getString(request,"userAuthStr");
        ObjectMapper mapper = new ObjectMapper();
        User user = null;
        UserAuth userAuth = null;
        try{
            user = mapper.readValue(userStr,User.class);
            userAuth = mapper.readValue(userAuthStr,UserAuth.class);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        if(user!=null&&userAuth!=null) {
            UserExecution ue;
            try{
                user.setUserId((Long)request.getSession().getAttribute("userId"));
                ue = userService.modify(user,userAuth);
                if(ue.getState()==UserStateEnum.SUCCESS.getState()){
                    modelMap.put("success",true);
                }else{
                    modelMap.put("success",false);
                    modelMap.put("errMsg",ue.getStateInfo());
                }
            }catch (UserOperationExecution e){
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
            return modelMap;
        }else{
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入用户Id");
            return modelMap;
        }
    }

}
