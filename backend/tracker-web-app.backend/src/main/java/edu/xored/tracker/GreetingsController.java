package edu.xored.tracker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/hello")
public class GreetingsController {

    @GetMapping
    public String greeting() {
        return "Hello, world";
    }

}
