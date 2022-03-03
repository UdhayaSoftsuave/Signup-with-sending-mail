package com.example.demo.appUser;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.MailSender.MailSender;
import com.example.demo.Registration.token.ConfirmationToken;
import com.example.demo.Registration.token.ConfirmationTokenService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService{
	
	private static final String USER_NOT_FOUND_MSG = "user with email %s is not found ";
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private ConfirmationTokenService confirmationTokenService;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserDetails user =(UserDetails)userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
		return user;
	}

	public String signupUser(AppUser appUser) {
		Boolean isExist = userRepository.existsByEmail(appUser.getUsername());
//		if (isExist) {
//			throw new IllegalStateException("Email already Exist");
//		}
		appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
		AppUser  result =userRepository.save(appUser);
		
		String token = UUID.randomUUID().toString();
		
		ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusHours(12), appUser);
		
		String tokenResult = confirmationTokenService.saveToken(confirmationToken);
		
		mailSender.send(result.getUsername(), sendMailHtml("http://localhost:8080/api/v1/register/confirm/" + token));
		
		return tokenResult;
	}
	
	private static String sendMailHtml(String url) {
		
		String Message =String.format("<a href=%s>Click Here to Confirm</a>",url);
		
		return Message;
		
	}

}
