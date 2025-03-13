package com.poseidoncapitalsolutions.trading.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller responsible for handling login page requests.
 * Provides an endpoint for displaying the login page.
 */
@Controller
public class LoginController {

    /**
     * Displays the login page.
     * 
     * @return A ModelAndView object containing the view name for the login page.
     */
    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }
}
