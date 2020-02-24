package web.hospitaladmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="hospitaladmin",method= RequestMethod.GET)
public class HospitalAdminController {
    @RequestMapping(value="/hospitaloperation")
    public String hospitalOperation(){
        return "hospital/hospitaloperation";
    }
    @RequestMapping(value="/hospitallist")
    public String shopList(){
        return "hospital/hospitallist";
    }
    @RequestMapping(value="/hospitalmanagement")
    public String shopManagement(){
        return "hospital/hospitalmanagement";
    }
}