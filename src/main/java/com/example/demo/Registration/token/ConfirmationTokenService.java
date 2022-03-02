package com.example.demo.Registration.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenService {
	
	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;
	
	public String saveToken(ConfirmationToken confirmationToken) {
		ConfirmationToken token = confirmationTokenRepository.save(confirmationToken);
		return token.getToken();
		
	}

}
