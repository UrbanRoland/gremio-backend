package com.gremio.service;

import com.gremio.enums.RoleType;
import com.gremio.exception.NotFoundException;
import com.gremio.model.input.UserInput;
import com.gremio.persistence.entity.User;
import com.gremio.repository.UserRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    
    @InjectMocks
    private UserServiceImpl userService;
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private ConversionService conversionService;

    @Mock
    private PasswordEncoder passwordEncoder;
    
    private User user;
    private UserInput userDto;

    @BeforeEach
    public void init() {
        this.user = User.builder()
            .firstName("Test")
            .lastName("Test")
            .email("test@test.com")
            .password("Test12345676")
            .build();
        
        user.setId(1L);
        
        this.userDto = UserInput.builder()
            .password("Test12345676")
            .role(RoleType.ROLE_READ_ONLY)
            .id(1L)
            .firstName("Test")
            .lastName("Test")
            .email("test@test.com")
            .build();
        
    }

    @Test
    public void UserService_FindUserByEmail_ReturnsUser() {
        Mockito.when(userRepository.findUserByEmail(Mockito.anyString())).thenReturn(user);
        final User existUser = userService.findUserByEmail(user.getEmail());
        
        Assertions.assertNotNull(existUser);
        
    }
    
    @Test
    public void UserService_CreateUser_ReturnsUser() {
        Mockito.when(userRepository.findUserByEmail(Mockito.anyString())).thenReturn(null);
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("encodedPassword");
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(conversionService.convert(Mockito.any(UserInput.class), Mockito.eq(User.class))).thenReturn(user);
        
        final User savedUser = userService.create(userDto).block();
        
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals("encodedPassword",savedUser.getPassword());
        Assertions.assertEquals(RoleType.ROLE_READ_ONLY, savedUser.getRole());

    }
    @Test
    public void UserService_CreateUser_ThrowsValidationException() {
        Assertions.assertThrows(ValidationException.class, () -> userService.create(userDto));
    }

    @Test
    public void UserService_LoadUserByUserName_ReturnsUserDetails() {
        Mockito.when(userRepository.findUserByEmail(user.getEmail())).thenReturn(user);
        UserDetails userDetails = userService.loadUserByUsername(user.getEmail());
        
        Assertions.assertNotNull(userDetails);
        Mockito.verify(userRepository).findUserByEmail(user.getEmail());
    }
    
    @Test
    public void UserService_LoadUserByUserName_ThrowsNotFoundException() {
        Mockito.when(userRepository.findUserByEmail(user.getEmail())).thenReturn(null);
    
        Assertions.assertThrows(NotFoundException.class, () -> {
            userService.loadUserByUsername(user.getEmail());
        });

        Mockito.verify(userRepository).findUserByEmail(user.getEmail());
    }
    
    @Test
    public void UserService_Update_ReturnUser() {
        String encodedPassword = "encodedTest123";

        
        Mockito.when(userRepository.getReferenceById(userDto.id())).thenReturn(user);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(passwordEncoder.encode(userDto.password())).thenReturn(encodedPassword);
    
        User updatedUser = userService.update(userDto);
    
        Assertions.assertEquals(userDto.email(), updatedUser.getEmail());
        Assertions.assertEquals(encodedPassword, updatedUser.getPassword());
        Assertions.assertEquals(userDto.role().getName(), updatedUser.getRole().getName());
    
    }
}