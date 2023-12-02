package com.gremio.controller;

import com.gremio.service.interfaces.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    
    @InjectMocks
    private UserController userController;
    
    @Mock
    private UserService userService;
    
    @Test
    public void UserController_GetAllUsers_CallsUserService() {
        Mockito.when(userService.getAllUser(Mockito.any(Pageable.class))).thenReturn(new PageImpl<>(new ArrayList<>()));
    
        this.userController.getAllUsers(PageRequest.of(0, 1));
        Mockito.verify(userService).getAllUser(Mockito.any(Pageable.class));
    }
}