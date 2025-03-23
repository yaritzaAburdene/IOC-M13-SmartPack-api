package com.smartpack.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controler Init
 * per mostrar pagines estatiques
 */
@Controller
public class InitController {

    /**
     * Pagina d'inici de la documentació
     * 
     * @return String
     */
    @GetMapping("/")
    public String homePage() {
        return "index";
    }

    /**
     * Pagina de la documentació javadoc
     * 
     * @return String
     */
    @GetMapping("/javadoc")
    public String redirectToDoc() {
        return "redirect:/javadoc/index.html";
    }
}
