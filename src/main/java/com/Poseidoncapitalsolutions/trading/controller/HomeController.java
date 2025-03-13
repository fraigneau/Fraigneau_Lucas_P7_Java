package com.poseidoncapitalsolutions.trading.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller responsible for handling the home page request.
 * Provides an endpoint for displaying the home page of the application.
 */
@Controller
public class HomeController {

	/**
	 * Displays the home page.
	 * 
	 * @param model The model to pass any necessary attributes to the view.
	 * @return The view name for the home page.
	 */
	@GetMapping("/")
	public String home(Model model) {
		return "home";
	}
}
