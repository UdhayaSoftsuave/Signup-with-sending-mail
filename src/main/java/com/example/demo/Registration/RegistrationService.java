package com.example.demo.Registration;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.Jsr310Converters.LocalTimeToDateConverter;
import org.springframework.stereotype.Service;

import com.example.demo.Registration.token.ConfirmationToken;
import com.example.demo.Registration.token.ConfirmationTokenRepository;
import com.example.demo.appUser.AppUser;
import com.example.demo.appUser.AppUserRole;
import com.example.demo.appUser.AppUserService;
import com.example.demo.appUser.EmailValidator;
import com.example.demo.appUser.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegistrationService {
	

	private EmailValidator emailValidator;
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository; 
	
	public String register(RegistrationRequest registrationRequest) {
//		emailValidator.Test(registrationRequest.getEmail());
		return appUserService.signupUser(
				new AppUser(registrationRequest.getFirstName(), registrationRequest.getLastName(), registrationRequest.getEmail(), registrationRequest.getPassword(), AppUserRole.USER));
	}

	public String confirmToken(String token) {
		ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token).get();
//		if (confirmationToken.getConfirmedAt() != null) {
//			throw new IllegalStateException("email already confirmed");
//		}
//		if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
//			throw new IllegalStateException("token expried");
//		}
				confirmationTokenRepository.findByToken(token)
				.ifPresent(ConformToken -> {
					ConformToken.setConfirmedAt(LocalDateTime.now());
					confirmationTokenRepository.save(ConformToken);
				});
		
				
		String result = "not confirmed";
				
		ConfirmationToken confirmation = confirmationTokenRepository.findByToken(token).get();
		LocalDateTime  dateTime=confirmation.getConfirmedAt();
		
		userRepository.findByEmail(confirmation.getAppUser().getUsername())
		.ifPresent(user ->{
			user.setEnabled(true);
			userRepository.save(user);
		});
		
		if (dateTime != null) {
			result = "confirmed";
		}
		return result;
		
		
		
	}

}
