package est.wordwise.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    
    @GetMapping("/")
    public String hello() {
        return "Welcome to WordWise API";
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "OK";
    }
}