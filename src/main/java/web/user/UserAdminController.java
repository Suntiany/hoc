package web.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/user")
public class UserAdminController {
    @RequestMapping(value = "/useroperation",method = RequestMethod.GET)
    public String userOperation(){
        return "user/useroperation";
    }
    @RequestMapping(value="/getuserbysessionId",method=RequestMethod.GET)
    public String UserSession(){
        return "user/useredit";
    }
    @RequestMapping(value="/signdetail",method = RequestMethod.GET)
    public String getHospitalDetail(){return "user/signdetail";}
    @RequestMapping(value="/doctordetail",method = RequestMethod.GET)
    public String getDoctorDetail(){return "user/doctordetail";}
    @RequestMapping(value="/healthmonitor",method = RequestMethod.GET)
    public String HealthMonitor(){
        return "user/healthmonitor";
    }
}
