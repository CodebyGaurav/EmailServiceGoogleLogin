package com.codebygaurav.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SocialOAuth2Controller {
	
	@GetMapping
	private String googleOAuth2() {
		return "Welcome to Google !!";	
	}
	
	@GetMapping("/user")
	private Principal user(Principal principal) {
		System.out.println("username : "+principal.getName());
		return principal;	
	}

}
