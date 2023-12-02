package com.gremio.service;

import com.gremio.enums.RoleType;
import com.gremio.exception.NotFoundException;
import com.gremio.message.NotFoundMessageKey;
import com.gremio.model.dto.UserDetailsDto;
import com.gremio.model.dto.UserDto;
import com.gremio.persistence.entity.User;
import com.gremio.repository.UserRepository;
import com.gremio.service.interfaces.UserService;
import jakarta.validation.ValidationException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    private final ConversionService conversionService;

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
    public Optional<UserDetailsDto> findById(final Long id) {
        return userRepository.findById(id).map(user -> conversionService.convert(user, UserDetailsDto.class));
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public User create(final User user) {
        if (userRepository.findUserByEmail(user.getEmail()) != null) {
            throw new ValidationException("email already taken");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
    public Page<UserDetailsDto> getAllUser(final Pageable pageable) {
       return userRepository.findAll(pageable).map(user -> conversionService.convert(user, UserDetailsDto.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User update(final long id, final UserDto userDto) {
        final User user = userRepository.getReferenceById(id);

        if (userDto.getEmail() != null && !userDto.getEmail().equals("")) {
            if (!userDto.getEmail().equals(user.getEmail()) && userRepository.findUserByEmail(userDto.getEmail()) != null) {
                throw new ValidationException("Email already taken!");
            }
            user.setEmail(userDto.getEmail());
        }

        if (userDto.getPassword() != null && !userDto.getPassword().equals("")) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        user.setRole(userDto.getRole());

        return userRepository.save(user);
    }
}