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
}
