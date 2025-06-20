package com.amadeomartell.WordStreamTranslator.controller.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/translate/")
public class TestController {

    @GetMapping
    public String test() {
        return "Hello World!";
    }
}
