package com.abbieschenk.ludosystems.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @Value("${ludosystems.app.url}")
    private String appUrl;

    @GetMapping()
    public String redirect() {
        return "redirect:" + appUrl;
    }
}
