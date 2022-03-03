package com.example.demo.Registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/register")
public class RegistrationController {
	
	@Autowired
	private RegistrationService registrationService;
	
	@PostMapping
	public String register(@RequestBody RegistrationRequest registrationRequest) {
		return registrationService.register(registrationRequest);
		
	}
	@GetMapping("/confirm/{token}")
	public String confirm(@PathVariable String token) {
		return registrationService.confirmToken(token);
		
	}
	
	
	

}
