package com.dockerjenkins.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/welcome")
public class WelcomeController {

	@RequestMapping(method = RequestMethod.GET,path = "/hi-docker")
	public String sayWelcome() {
		return "welcome Docker Jenkins";
	}
	
	
	
}
