package com.gremio.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gremio.exception.NotFoundException;
import com.gremio.model.dto.request.AuthRequest;
import com.gremio.model.dto.response.AuthResponse;
import com.gremio.persistence.entity.User;
import com.gremio.service.interfaces.JwtService;
import com.gremio.service.interfaces.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class AuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    private static final String LOGIN_ENDPOINT = "/login";
    private final AuthenticationManager authenticationManager;
    private final ConversionService conversionService;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthenticationProcessingFilter(final AuthenticationManager authenticationManager,
                                          final ConversionService conversionService,
                                          final JwtService jwtService,
                                          final UserService userService) {
        super(new AntPathRequestMatcher(LOGIN_ENDPOINT, HttpMethod.POST.name()));
        this.authenticationManager = authenticationManager;
        this.conversionService = conversionService;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public final Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) throws AuthenticationException {
        try {
            final AuthRequest authRequest = Optional.ofNullable(new ObjectMapper().readValue(request.getInputStream(), AuthRequest.class))
                    .orElseThrow(() -> new PreAuthenticatedCredentialsNotFoundException("not found"));

            if (userService.findUserByEmail(authRequest.getEmail()) == null) {
                throw new PreAuthenticatedCredentialsNotFoundException("not found");
            }

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(),
                            authRequest.getPassword(),
                            new ArrayList<>())
            );
        } catch (final IOException | NotFoundException exception) {

            throw new PreAuthenticatedCredentialsNotFoundException("credentials-not-found");
        }
    }

    @Override
    protected final void successfulAuthentication(final HttpServletRequest request,
                                                  final HttpServletResponse response,
                                                  final FilterChain chain,
                                                  final Authentication auth)
            throws IOException {

        final User user = (User)auth.getPrincipal();
        user.setRefreshToken(jwtService.generateRefreshToken(user));
        userService.save(user);

        final AuthResponse authResponse = Optional.ofNullable(conversionService.convert(user, AuthResponse.class))
                .orElseThrow(() -> new PreAuthenticatedCredentialsNotFoundException("credential-not-found"));
        authResponse.setAccessToken(jwtService.generateToken(user));

        response.setHeader("Authorization", "Bearer " + authResponse.getAccessToken());
        new ObjectMapper().writeValue(response.getWriter(), authResponse);
    }

    @Override
    protected final void unsuccessfulAuthentication(final HttpServletRequest request,
                                                    final HttpServletResponse response,
                                                    final AuthenticationException exception)
            throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        new ObjectMapper().writeValue(response.getWriter(), "You have entered either the email and/or password incorrectly!");
    }
}