package com.gremio.controller;

import com.gremio.model.dto.UserDetailsDto;
import com.gremio.model.dto.request.CreateUserRequest;
import com.gremio.model.dto.request.TokenRefreshRequest;
import com.gremio.model.dto.response.AuthResponse;
import com.gremio.persistence.entity.User;
import com.gremio.service.interfaces.JwtService;
import com.gremio.service.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    private final ConversionService conversionService;
    private final JwtService jwtService;

    /**
     * Creates a new user in the system.
     *
     * @param request The CreateUserRequest containing the user details to create.
     * @return The UserDetailsDto of the newly created user.
     */
    @PostMapping
    public UserDetailsDto createUser(@RequestBody @Valid final CreateUserRequest request) {
        final User user = userService.create(conversionService.convert(request, User.class));

       return conversionService.convert(user, UserDetailsDto.class);

    }

    /**
     * Refreshes the authentication token using the provided refresh token.
     *
     * @param tokenRequest The TokenRefreshRequest containing the refresh token.
     * @return An AuthResponse containing the new access token if successful, or null if the user is not found.
     */
    @PostMapping("/token/refresh")
    public AuthResponse refreshToken(@Valid @RequestBody final TokenRefreshRequest tokenRequest) {
        return jwtService.refreshAuthToken(tokenRequest.getRefreshToken());
    }
}