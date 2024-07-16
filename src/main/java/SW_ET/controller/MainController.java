package SW_ET.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    // 로그인 경로 처리 제거
    @RequestMapping("/user")
    public String user(){
        return "user/index";
    }
}