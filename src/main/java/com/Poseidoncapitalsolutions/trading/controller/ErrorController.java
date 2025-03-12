package com.poseidoncapitalsolutions.trading.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/403")
    public String error(Model model) {
        String errorMessage = "You are not authorized for the requested data.";
        model.addAttribute("errorMsg", errorMessage);
        return "error/403";
    }
}
