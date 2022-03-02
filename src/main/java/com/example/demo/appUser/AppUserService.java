package com.example.demo.appUser;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
	private ConfirmationTokenService confirmationTokenService;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
	}

	public String signupUser(AppUser appUser) {
		Boolean isExist = userRepository.existsByEmail(appUser.getUsername());
		if (isExist) {
			throw new IllegalStateException("Email already Exist");
		}
		appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
		appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
		appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
		AppUser  result =userRepository.save(appUser);
		
		String token = UUID.randomUUID().toString();
		
		ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now(), appUser);
		
		String tokenResult = confirmationTokenService.saveToken(confirmationToken);
		
		return tokenResult;
	}

}
