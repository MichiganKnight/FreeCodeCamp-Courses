package com.revature.springControllerExample;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/API")
public class ApiController {
    @GetMapping("/Status")
    public Map<String, String> getStatus() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "running");
        response.put("framework", "Spring Boot");
        return response;
    }

    @PostMapping("/Submit")
    public ResponseEntity<String> submitData(@RequestBody MyDTO data) {
        String message = String.format("Received Data: %s - %d Years Old", data.getName(), data.getAge());

        return ResponseEntity.ok(message);
    }
}
