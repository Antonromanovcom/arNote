package com.antonromanov.arnote.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Контроллер, отвечающий за фронтенд
 */
@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class WebController {

	@RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
	public String welcome() {
		return "index";
	}

}

