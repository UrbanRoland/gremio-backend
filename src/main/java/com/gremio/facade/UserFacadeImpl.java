package com.gremio.facade;

import com.gremio.enums.UserMessageKey;
import com.gremio.exception.NotFoundException;
import com.gremio.exception.UserException;
import com.gremio.message.NotFoundMessageKey;
import com.gremio.persistence.entity.PasswordResetToken;
import com.gremio.persistence.entity.User;
import com.gremio.repository.PasswordResetTokenRepository;
import com.gremio.service.interfaces.EmailService;
import com.gremio.service.interfaces.UserService;
import java.time.Duration;;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class UserFacadeImpl implements  UserFacade {
    private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;
    private final UserService userService;
    private final EmailService emailService;
    private final PasswordResetTokenRepository resetTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String forgotPassword(final String email) {
        final User user = userService.findUserByEmail(email);

        if (user == null) {
          throw  new NotFoundException(NotFoundMessageKey.USER);
        }
        String token = generateToken();
        createPasswordResetTokenForUser(user, token);
        emailService.forgotPasswordEmail(user.getEmail(), "Reset Password","resetPassword", token, user.getUsername());

        return "success";
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(final String password, final String token) {
        final PasswordResetToken resetToken = resetTokenRepository.findByToken(token);
        
        if(resetToken == null) {
            throw new UserException(UserMessageKey.INVALID_TOKEN);
        }
    
        if (isTokenExpired(resetToken.getCreationDate())) {
            //todo exception class and format message
            throw new IllegalArgumentException("Token expired.");
        }
        
        resetTokenRepository.deleteById(resetToken.getId());
        
        final User user = resetToken.getUser();
        final String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        userService.save(user);
        
        //todo send an email to password successful updated
        
        System.out.println(resetToken.getUser().getEmail());
    }
    
    
    private String generateToken() {
        return UUID.randomUUID() + UUID.randomUUID().toString();
    }

    private void createPasswordResetTokenForUser(final User user, final String token) {
        final Optional<PasswordResetToken> optionalToken = resetTokenRepository.findByUserId(user.getId());

       final PasswordResetToken passwordResetToken = optionalToken.orElseGet(() ->
           PasswordResetToken.builder()
               .user(user)
               .build()
        );

        updateTokenAndDate(passwordResetToken, token);
        resetTokenRepository.save(passwordResetToken);
    }
    private void updateTokenAndDate(final PasswordResetToken passwordResetToken, final String token) {
        passwordResetToken.setToken(token);
        passwordResetToken.setCreationDate(LocalDateTime.now());
    }
    
    
    private boolean isTokenExpired(LocalDateTime tokenCreationDate) {
        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(tokenCreationDate, now);
        
        return diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;
        
    }
}