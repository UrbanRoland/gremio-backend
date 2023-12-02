package com.gremio.controller;

import com.gremio.model.dto.UserDetailsDto;
import com.gremio.model.dto.request.CreateUserRequest;
import com.gremio.persistence.entity.User;
import com.gremio.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {
    
    @Mock
    private UserServiceImpl userService;
    
    @Mock
    private ConversionService conversionService;
    
    @InjectMocks
    private AuthenticationController authenticationController;
    
    
    @Test
    public void createUser_ValidRequest_ReturnsUserDetailsDto() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        User createdUser = new User();
        createdUser.setId(1L);
        UserDetailsDto expectedResponse = new UserDetailsDto();
        expectedResponse.setId(1L);
        Mockito.when(userService.create(Mockito.any(User.class))).thenReturn(createdUser);
        Mockito.when(conversionService.convert(createUserRequest, User.class)).thenReturn(createdUser);
        Mockito.when(conversionService.convert(createdUser, UserDetailsDto.class)).thenReturn(expectedResponse);
        
        UserDetailsDto actualResponse = authenticationController.createUser(createUserRequest);
        
        Mockito.verify(userService, Mockito.times(1)).create(Mockito.any(User.class));
        Mockito.verify(conversionService, Mockito.times(1)).convert(createUserRequest, User.class);
        Mockito.verify(conversionService, Mockito.times(1)).convert(createdUser, UserDetailsDto.class);
        Assertions.assertEquals(expectedResponse, actualResponse);
    }
}