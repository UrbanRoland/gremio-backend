package com.gremio.controller;

import com.gremio.facade.UserFacade;
import com.gremio.model.dto.TokenRefreshInput;
import com.gremio.model.dto.UserDto;
import com.gremio.model.dto.response.AuthResponse;
import com.gremio.persistence.entity.User;
import com.gremio.service.interfaces.JwtService;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserFacade userFacade;
    private final JwtService jwtService;

    /**
     * Creates a new user in the system.
     *
     * @param user containing the user details to create.
     * @return The UserDetailsDto of the newly created user.
     */
    @MutationMapping
    public User registration(@Argument final UserDto user) {
        return userFacade.userRegistration(user);
    }

    /**
     * Refreshes the authentication token using the provided refresh token.
     *
     * @param tokenRequest The TokenRefreshRequest containing the refresh token.
     * @return An AuthResponse containing the new access token if successful, or null if the user is not found.
     */
    @MutationMapping
    public AuthResponse refreshToken(@Argument final TokenRefreshInput tokenRequest) {
        return jwtService.refreshAuthToken(tokenRequest.refreshToken());
    }

    /**
     * Authenticates a user and returns an AuthResponse containing the access token and refresh token.
     *
     * @param email The email of the user to authenticate.
     * @param password The password of the user to authenticate.
     * @return An AuthResponse containing the access token and refresh token.
     */
    @MutationMapping
    public AuthResponse login(@NotEmpty @Argument final String email, @NotEmpty @Argument final String password) {
        return userFacade.login(email, password);
    }

}