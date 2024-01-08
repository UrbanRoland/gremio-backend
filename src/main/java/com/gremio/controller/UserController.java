package com.gremio.controller;

import com.gremio.facade.UserFacade;
import com.gremio.model.dto.EmailDto;
import com.gremio.model.dto.UserDto;
import com.gremio.model.dto.response.archive.PageableResponse;
import com.gremio.persistence.entity.User;
import com.gremio.repository.UserRepository;
import com.gremio.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserFacade userFacade;
    
    private final UserRepository userRepository;

    /**
     * Update an existing user.
     *
     * @param  user The user input.
     * @return the updated user
     */
    @MutationMapping
    @PreAuthorize("isAuthenticated()")
    public User updateUserData(@Argument final UserDto user) {
        return userService.update(user);
    }

    /**
     * Sends a password reset email to the user with the given email address.
     *
     * @param emailDto The email address of the user to send the password reset email to.
     * @return A string indicating the result of the operation.
     */
    @MutationMapping
    public String forgotPassword(@Argument final EmailDto emailDto) {
       return userFacade.forgotPassword(emailDto.email());
    }

    /**
     * Resets a user's password.
     *
     * @param userDto The user input.
     * @return A string indicating the result of the operation.
     */
    @MutationMapping
    public String resetPassword(@Argument final UserDto userDto) {
       return userFacade.resetPassword(userDto.password(), userDto.token());
    }

    /**
     * Retrieves all users from the system.
     * @return Existing users.
     */
    @MutationMapping
    public Page<User> allUsersPaged(@Argument final int page, @Argument final int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return userRepository.findAll(pageRequest);
    }
    
    
}