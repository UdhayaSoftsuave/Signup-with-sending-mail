package com.example.demo.Registration;

import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

	public String register(RegistrationRequest registrationRequest) {
		return "success";
	}

}
