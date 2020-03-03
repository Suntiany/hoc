package web.login;

import dao.AreaAuthDao;
import entity.AreaAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.AreaAuthService;
import util.CodeUtil;
import util.HttpServletRequestUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value="/local",method = {RequestMethod.GET,RequestMethod.POST})
public class AreaAuthController {
    @Autowired
    private AreaAuthService areaAuthService;

    @RequestMapping(value="/areaadminlogin",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> Arealogincheck(HttpServletRequest request) {
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
            // 传入帐号和密码去获取平台帐号信息
            AreaAuth areaAuth = areaAuthService.Login(username,password);
            if (areaAuth != null) {
                // 若能取到帐号信息则登录成功
                modelMap.put("success", true);
                // 同时在session里设置用户信息
                request.getSession().setAttribute("areaId", areaAuth.getArea().getAreaId());
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "用户名或密码错误");
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户名和密码均不能为空");
        }
        return modelMap;
    }

    @RequestMapping(value="/arealogout",method=RequestMethod.POST)
    @ResponseBody
    /**
     * 用户点击登出按钮的时候清理session
     */
    private Map<String,Object> logout(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<String, Object>();
        //将地区session置为空
        request.getSession().setAttribute("areaId",null);
        modelMap.put("success",true);
        return modelMap;
    }

    @RequestMapping(value = "/changelocalpwd", method = RequestMethod.POST)
    @ResponseBody
    /**
     * 修改密码
     *
     * @param request
     * @return
     */
    private Map<String, Object> changeLocalPwd(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        return modelMap;
    }
}
