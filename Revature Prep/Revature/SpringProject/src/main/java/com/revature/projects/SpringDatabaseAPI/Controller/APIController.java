package com.revature.projects.SpringDatabaseAPI.Controller;

import com.revature.projects.SpringDatabaseAPI.Enums.Status;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/API")
public class APIController {
    @GetMapping("/Status")
    public String getStatus() {
        Status response = Status.RUNNING;
        String status = response.toString();

        if (response == Status.RUNNING) {
            return status;
        } else if (response == Status.STOPPED) {
            return "Server Stopped";
        } else {
            return Status.ERROR.toString();
        }
    }
}
