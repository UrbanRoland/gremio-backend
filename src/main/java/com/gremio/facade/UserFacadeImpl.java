package com.gremio.facade;

import com.gremio.enums.UserMessageKey;
import com.gremio.exception.NotFoundException;
import com.gremio.exception.UserException;
import com.gremio.message.NotFoundMessageKey;
import com.gremio.model.dto.response.AuthResponse;
import com.gremio.model.input.UserInput;
import com.gremio.persistence.entity.PasswordResetToken;
import com.gremio.persistence.entity.User;
import com.gremio.repository.PasswordResetTokenRepository;
import com.gremio.service.interfaces.EmailService;
import com.gremio.service.interfaces.JwtService;
import com.gremio.service.interfaces.UserService;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
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
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public String forgotPassword(final String email) {
        final User user = userService.findUserByEmail(email);
    
        if (user == null) {
            throw new NotFoundException(NotFoundMessageKey.USER);
        }
        final String token = generateToken();
        createPasswordResetTokenForUser(user, token);
        emailService.forgotPasswordEmail(user.getEmail(), "Reset Password", "resetPassword", token, user.getUsername());

        return "Email sent successfully";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String resetPassword(final String password, final String token) {
        final PasswordResetToken resetToken = resetTokenRepository.findByToken(token);
        
        if (resetToken == null) {
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
        return "Password reset successfully";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthResponse login(final String email, final String password) {
        return attemptAuthentication(email, password);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User userRegistration(final UserInput user) {
        return userService.create(user);
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

    private boolean isTokenExpired(final LocalDateTime tokenCreationDate) {
        final LocalDateTime now = LocalDateTime.now();
        final Duration diff = Duration.between(tokenCreationDate, now);

        return diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;
    }

    private AuthResponse attemptAuthentication(final String email, final String password) throws AuthenticationException {
        final User user = userService.findUserByEmail(email);
        if (user == null) {
            throw new NotFoundException(NotFoundMessageKey.USER);
        }

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                email,
                password,
                new ArrayList<>())
        );

        user.setRefreshToken(jwtService.generateRefreshToken(user));
        userService.save(user);

        return AuthResponse.builder()
            .accessToken(jwtService.generateToken(user))
            .build();
    }

}