package web.login;

import entity.DoctorAuth;
import entity.UserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.DoctorAuthService;
import util.CodeUtil;
import util.HttpServletRequestUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/doctor")
public class DoctorAuthController {
    @Autowired
    private DoctorAuthService doctorAuthService;

    @RequestMapping(value="/doctorlogin",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> DoctorLoginCheck(HttpServletRequest request) {
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
        if (username != null && password != null){
            DoctorAuth doctorAuth = doctorAuthService.login(username,password);
            if(doctorAuth!=null){
                modelMap.put("success",true);
                request.getSession().setAttribute("doctorId",doctorAuth.getDoctor().getDoctorId());
            }else{
                modelMap.put("success", false);
                modelMap.put("errMsg", "用户名或密码错误");
            }
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户名和密码均不能为空");
        }
        return modelMap;
    }

    @RequestMapping(value="/doctorlogout",method = RequestMethod.POST)
    @ResponseBody
    /**
     * 医生登出时候清理session
     */
    private Map<String,Object> logout(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<String, Object>();
        //将地区session置为空
        request.getSession().setAttribute("doctorId",null);
        modelMap.put("success",true);
        return modelMap;
    }

    @RequestMapping(value="/doctorchangepsw",method = RequestMethod.POST)
    @ResponseBody
    /**
     * 医生更改自己医生系统的登录密码
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
        // 从session中获取当前用户信息
        Long doctorId = (Long)request.getSession().getAttribute("doctorId");
        //非空判断
        if(userName != null && password != null && newPassword != null &&doctorId>-1 && !password.equals(newPassword)){
            try{
                DoctorAuth doctorAuth = doctorAuthService.getDoctorAuthByDoctorId(doctorId);
                if(doctorAuth==null || !doctorAuth.getUsername().equals(userName)) {
                    //不一致则退出
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "输入的帐号非本次登录的帐号");
                    return modelMap;
                }
                //修改账号和密码
                int effectedNum = doctorAuthService.changePass(doctorId,userName,password,newPassword);
                if(effectedNum>0){
                    modelMap.put("success",true);
                }else{
                    modelMap.put("success",false);
                }
            }catch (Exception e){
                modelMap.put("success", false);
                modelMap.put("errMsg", "密码错误");
                return modelMap;
            }
        }else{
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入密码");
        }
        return modelMap;
    }
}
