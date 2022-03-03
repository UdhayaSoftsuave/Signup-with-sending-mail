package com.example.demo.Registration.token;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
	Optional<ConfirmationToken> findByToken(String token);

	Optional<ConfirmationToken> findOneByToken(String token);

}
