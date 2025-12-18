package com.revature.projects.SpringDatabaseAPI.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebPageController {
    @RequestMapping("/")
    public @ResponseBody String index() {
        return "Home <br/> <a href='/Error'>Error Page</a> <br/> <a href='/API/Status'>API Status</a>";
    }

    @RequestMapping("/Error")
    public @ResponseBody String error() {
        return "Error Page <br/> <a href='/'>Home</a>";
    }
}
