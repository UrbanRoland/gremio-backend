package com.gremio.repository;

import com.gremio.persistence.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByUserId(Long id);
    
    PasswordResetToken findByToken(String token);
    
    void deleteByUserId(Long id);
}