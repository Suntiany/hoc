package web.login;

import dto.UserAuthExecution;
import entity.Doctor;
import entity.User;
import entity.UserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.UserAuthService;
import service.UserService;
import util.CodeUtil;
import util.HttpServletRequestUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserAuthController {
    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private UserService userService;

    @RequestMapping(value="/userlogin",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> UserLoginCheck(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<String, Object>();
        // 获取是否需要进行验证码校验的标识符
        boolean needVerify = HttpServletRequestUtil.getBoolean(request, "needVerify");
        if (needVerify && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        // 获取输入的帐号
        String username = HttpServletRequestUtil.getString(request, "username");
        // 获取输入的密码
        String password = HttpServletRequestUtil.getString(request, "password");
        // 非空校验
        if (username != null && password != null) {
            //传入用户账号和密码去登录
            UserAuth userAuth = userAuthService.Login(username,password);
            long userId = userAuth.getUser().getUserId();
            User user = userService.getByUserId(userId);
            if(user.getEnableStatus()==0){
                modelMap.put("success",false);
                return modelMap;
            }
            if(userAuth!=null) {
                modelMap.put("success",true);
                request.getSession().setAttribute("userId",userAuth.getUser().getUserId());
            }else{
                modelMap.put("success", false);
                modelMap.put("errMsg", "用户名或密码错误");
            }
        }else{
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户名和密码均不能为空");
        }
        return modelMap;
    }

    @RequestMapping(value="/userlogout",method=RequestMethod.POST)
    @ResponseBody
    /**
     * 用户点击登出按钮的时候清理session
     */
    private Map<String,Object> logout(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<String, Object>();
        //将地区session置为空
        request.getSession().setAttribute("userId",null);
        modelMap.put("success",true);
        return modelMap;
    }

    @RequestMapping(value="/userchangepsw",method =RequestMethod.POST)
    @ResponseBody
    /**
     * 用户更改自己的密码
     */
    private Map<String,Object> changePas(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        // 获取帐号
        String userName = HttpServletRequestUtil.getString(request, "userName");
        // 获取原密码
        String password = HttpServletRequestUtil.getString(request, "password");
        // 获取新密码
        String newPassword = HttpServletRequestUtil.getString(request, "newPassword");
        // 从session中获取当前用户信息(用户一旦通过微信登录之后，便能获取到用户的信息)
        Long userId = (Long)request.getSession().getAttribute("userId");
        //非空判断
        if(userName != null && password != null && newPassword != null &&userId>-1 && !password.equals(newPassword)){
            try{
                UserAuth userAuth = userAuthService.getUserAuthByUserId(userId);
                if(userAuth==null || !userAuth.getUsername().equals(userName)) {
                    //不一致则退出
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "输入的帐号非本次登录的帐号");
                    return modelMap;
                }
                //修改用户的平台账号和密码
                int effectedNum = userAuthService.changePass(userId,userName,password,newPassword);
                if(effectedNum>0){
                    modelMap.put("success",true);
                }else{
                    modelMap.put("success", false);
                }
            }catch (Exception e){
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        }else{
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入密码");
        }
        return modelMap;
    }
}
