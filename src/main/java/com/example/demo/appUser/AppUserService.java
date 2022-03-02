package com.example.demo.appUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService{
	
	private static final String USER_NOT_FOUND_MSG = "user with email %s is not found ";
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
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
		return result +"";
	}

}
