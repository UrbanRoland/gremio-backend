package com.gremio.service;

import com.gremio.enums.RoleType;
import com.gremio.exception.NotFoundException;
import com.gremio.message.NotFoundMessageKey;
import com.gremio.model.input.UserInput;
import com.gremio.persistence.entity.User;
import com.gremio.repository.UserRepository;
import com.gremio.service.interfaces.UserService;
import jakarta.validation.ValidationException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
//import samples.boot.AppConfig;

@Service
@RequiredArgsConstructor
@Slf4j
@CacheConfig(cacheNames = "userCache")
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConversionService conversionService;
   // private final AppConfig.Server config;

    /**
     * Retrieves the user details associated with the provided email.
     *
     * @param email The email address of the user to retrieve.
     * @return The user details corresponding to the given email.
     * @throws UsernameNotFoundException If the user with the provided email is not found.
     */
    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        return Optional.ofNullable(userRepository.findUserByEmail(email)).orElseThrow(() -> new NotFoundException(NotFoundMessageKey.USER));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findUserByEmail(final String email) {
        return userRepository.findUserByEmail(email);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User create(final UserInput userInput) {
        final User user = conversionService.convert(userInput, User.class);

        
        if (user == null || userRepository.findUserByEmail(user.getEmail()) != null) {
            throw new ValidationException("Something went wrong! Please try again later.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //set default role
        user.setRole(RoleType.ROLE_READ_ONLY);
        return userRepository.save(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(final User user) {
        userRepository.save(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @CachePut(cacheNames = "updateUser", key = "#userInput.id()")
    public User update(final UserInput userInput) {
        final User user = userRepository.getReferenceById(userInput.id());

        if (userInput.email() != null && !userInput.email().equals("")) {
            if (!userInput.email().equals(user.getEmail()) && userRepository.findUserByEmail(userInput.email()) != null) {
                throw new ValidationException("Something went wrong! Please try again later.");
            }
            user.setEmail(userInput.email());
        }

        if (userInput.password() != null && !userInput.password().equals("")) {
            user.setPassword(passwordEncoder.encode(userInput.password()));
        }

        user.setRole(userInput.role());

        return userRepository.save(user);
    }
}