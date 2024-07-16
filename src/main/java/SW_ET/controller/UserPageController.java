package SW_ET.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserPageController {

    @GetMapping("/myPage")
    public String showMyPage() {
        return "users/myPage";
    }
}