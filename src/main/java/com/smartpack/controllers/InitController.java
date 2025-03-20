package com.smartpack.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InitController {

    /**
     * Pagina d'inici de la documentació
     * 
     * @return
     */
    @GetMapping("/")
    public String homePage() {
        return "index";
    }
}
