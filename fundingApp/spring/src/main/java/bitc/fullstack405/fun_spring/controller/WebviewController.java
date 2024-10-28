package bitc.fullstack405.fun_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebviewController {

    @GetMapping("/search-address")
    public String searchAddress(){
        return "search_address";
    }
}
