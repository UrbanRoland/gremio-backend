package com.gremio.service;

import com.gremio.enums.RoleType;
import com.gremio.exception.NotFoundException;
import com.gremio.model.dto.UserDetailsDto;
import com.gremio.model.dto.UserDto;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    @BeforeEach
    public void init() {
        this.user = User.builder()
            .firstName("Test")
            .lastName("Test")
            .email("test@test.com")
            .password("Test12345676")
            .build();
        
        user.setId(1L);
    }
    @Test
    public void UserService_GetAllUser_ReturnsUserDetailsPage() {
        Pageable pageable = PageRequest.of(0, 10);
        List<User> users = Arrays.asList(user, user);
        Page<User> userPage = new PageImpl<>(users);
        List<UserDetailsDto> userDetails = Arrays.asList(new UserDetailsDto(), new UserDetailsDto());
        Page<UserDetailsDto> expectedPage = new PageImpl<>(userDetails);
        
        Mockito.when(userRepository.findAll(pageable)).thenReturn(userPage);
        
        Mockito.when(conversionService.convert(Mockito.any(User.class), Mockito.eq(UserDetailsDto.class)))
                .thenReturn(new UserDetailsDto());
        
        Page<UserDetailsDto> actualPage = userService.getAllUser(pageable);
        
        Assertions.assertEquals(expectedPage, actualPage);
        
        Mockito.verify(userRepository).findAll(pageable);
        
        Mockito.verify(conversionService, Mockito.times(users.size()))
                .convert(Mockito.any(User.class), Mockito.eq(UserDetailsDto.class));

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
        
        final User savedUser = userService.create(user);
        
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals("encodedPassword",savedUser.getPassword());
        Assertions.assertEquals(RoleType.ROLE_READ_ONLY, savedUser.getRole());

    }
    @Test
    public void UserService_CreateUser_ThrowsValidationException() {
        Mockito.when(userRepository.findUserByEmail(Mockito.anyString())).thenReturn(user);
    
        Assertions.assertThrows(ValidationException.class, () -> userService.create(user));
    }
    
    @Test
    public void UserService_FindUserById_ReturnsOptionalUser() {
        final UserDetailsDto userDetailsDto = UserDetailsDto.builder()
            .id(user.getId())
            .build();

        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
        Mockito.when(conversionService.convert(user, UserDetailsDto.class)).thenReturn(userDetailsDto);
    
        Optional<UserDetailsDto> existsUser = userService.findById(user.getId());
    
        Assertions.assertTrue(existsUser.isPresent());
        Assertions.assertEquals(userDetailsDto, existsUser.get());
        Mockito.verify(userRepository).findById(user.getId());
        
    }
    
    @Test
    public void UserService_FindUserById_ReturnsEmptyOptional() {
        final Long userId = 1L;
        
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        
        Optional<UserDetailsDto> existsUser = userService.findById(userId);
    
        Assertions.assertFalse(existsUser.isPresent());
    
        Mockito.verify(userRepository).findById(userId);
        Mockito.verifyNoInteractions(conversionService);
        
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
        UserDto userDto = UserDto.builder()
            .email("newemail@example.com")
            .role(RoleType.ROLE_READ_ONLY)
            .password("test123")
            .build();
        
        Mockito.when(userRepository.getReferenceById(user.getId())).thenReturn(user);
        Mockito.when(userRepository.findUserByEmail(userDto.getEmail())).thenReturn(null);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(passwordEncoder.encode(userDto.getPassword())).thenReturn(encodedPassword);
    
        User updatedUser = userService.update(user.getId(), userDto);
    
        Assertions.assertEquals(userDto.getEmail(), updatedUser.getEmail());
        Assertions.assertEquals(encodedPassword, updatedUser.getPassword());
        Assertions.assertEquals(userDto.getRole().getName(), updatedUser.getRole().getName());
    
    }
    
}