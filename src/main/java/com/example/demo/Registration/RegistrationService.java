package com.example.demo.Registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.appUser.AppUser;
import com.example.demo.appUser.AppUserRole;
import com.example.demo.appUser.AppUserService;
import com.example.demo.appUser.EmailValidator;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegistrationService {
	

	private EmailValidator emailValidator;
	@Autowired
	private AppUserService appUserService;

	public String register(RegistrationRequest registrationRequest) {
//		emailValidator.Test(registrationRequest.getEmail());
		return appUserService.signupUser(
				new AppUser(registrationRequest.getFirstName(), registrationRequest.getLastName(), registrationRequest.getEmail(), registrationRequest.getPassword(), AppUserRole.USER));
	}

}
