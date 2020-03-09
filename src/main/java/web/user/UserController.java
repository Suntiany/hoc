package web.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/user",method= RequestMethod.GET)
public class UserController {
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
}
