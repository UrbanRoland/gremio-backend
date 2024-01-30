package com.gremio.repository;

import com.gremio.persistence.domain.PasswordResetToken;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PasswordResetTokenRepository extends ReactiveCrudRepository<PasswordResetToken, Long> {
    Mono<PasswordResetToken> findByUserId(Long id);
    
    PasswordResetToken findByToken(String token);
    
    void deleteByUserId(Long id);
}