package web.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("local")
public class LoginController {
    @RequestMapping(value="/arealogin",method= RequestMethod.GET)
    private String login() {
        return "local/arealogin";
    }
}
