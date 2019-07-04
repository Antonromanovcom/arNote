package com.antonromanov.temprest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Контроллер, отвечающий за фронтенд
 */
@Controller
public class WebController {

	@RequestMapping(value = "/test", method = {RequestMethod.GET, RequestMethod.POST})
	private String test() {
		//model.addAttribute("testme", "Привет!");
		return "welcome";
	}

	@RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
	public String welcome() {
		return "index";
	}

}

