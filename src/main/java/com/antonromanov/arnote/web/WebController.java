package com.antonromanov.arnote.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Контроллер, отвечающий за фронтенд
 */
// todo: вот по ходу сюда надо вписать недостающие страницы, чтобы после протухания токена не падало
@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class WebController {

	@RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
	public String indexPage() {
		return "index";
	}

	@RequestMapping(value = "/investing", method = {RequestMethod.GET})
	public String investPage() {
		return "index";
	}

	@RequestMapping(value = "/401", method = {RequestMethod.GET, RequestMethod.POST})
	public String welcomePage() {
		return "index";
	}

}

