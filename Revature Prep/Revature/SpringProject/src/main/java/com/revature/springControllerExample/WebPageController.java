package com.revature.springControllerExample;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebPageController {
    @GetMapping("/Greeting")
    public String greet(Model model) {
        model.addAttribute("message", "Welcome to Spring MVC!");

        return "greeting";
    }
}
