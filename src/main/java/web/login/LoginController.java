package web.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("local")
public class LoginController {
    @RequestMapping(value="/arealogin",method= RequestMethod.GET)
    private String arealogin() {
        return "local/arealogin";
    }
    @RequestMapping(value="/userlogin",method = RequestMethod.GET)
    private String userlogin() {return "local/userlogin";}
    @RequestMapping(value="/doctorlogin",method = RequestMethod.GET)
    private String doctorligin(){return "local/doctorlogin";}
    /**
     * 转发请求到WEB_INF 里面的/doctor/changepsw.html
     */
    @RequestMapping(value="/changepsw")
    public String changepsw(){return "local/changepsw";}
}
