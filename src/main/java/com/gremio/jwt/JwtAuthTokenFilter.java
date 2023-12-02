package com.gremio.jwt;

import com.gremio.service.interfaces.JwtService;
import com.gremio.service.interfaces.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAuthTokenFilter extends BasicAuthenticationFilter {
    private static final int CONFLICT_CODE = 409;
    private final JwtService jwtService;
    private final UserService userService;
    public JwtAuthTokenFilter(final AuthenticationManager authManager, final JwtService jwtService, final UserService userService) {
        super(authManager);
        this.jwtService = jwtService;
        this.userService = userService;
    }

    /**
     * Filters the incoming request and processes the JWT token for authentication.
     * If a valid token is found, it authenticates the user using the provided authentication manager.
     *
     * @param request     The HTTP servlet request.
     * @param response    The HTTP servlet response.
     * @param filterChain The filter chain.
     * @throws ServletException If an error occurs during filtering.
     * @throws IOException      If an I/O error occurs during filtering.
     */
    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                final HttpServletResponse response,
                final FilterChain filterChain) throws ServletException, IOException {

        final String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            final String token = header.substring(7);
            try {
                processToken(token);
            } catch (final ExpiredJwtException | PreAuthenticatedCredentialsNotFoundException e) {
                response.setStatus(CONFLICT_CODE);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Processes the provided JWT token, extracts the username from it, and validates the token for the user.
     * If the token is valid, it proceeds to authenticate the user.
     *
     * @param token The JWT token to process.
     */
    private void processToken(final String token) {
        final String email = jwtService.extractUsername(token);
        final UserDetails user = loadUserByUsername(email);
        if (jwtService.isTokenValid(token, user)) {
            authenticateUser(user);
        }
    }

    /**
     * Authenticates the provided user by setting the user's authentication information in the security context.
     *
     * @param user The user details to be authenticated.
     */
    private void authenticateUser(final UserDetails user) {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
    }

    /**
     * Loads the user details based on the provided email.
     *
     * @param email The email of the user to load.
     * @return The UserDetails object representing the loaded user.
     * @throws PreAuthenticatedCredentialsNotFoundException If the user with the provided email is not found.
     */
    private UserDetails loadUserByUsername(final String email) throws PreAuthenticatedCredentialsNotFoundException {
        final UserDetails user = userService.loadUserByUsername(email);
        if (user == null) {
            throw new PreAuthenticatedCredentialsNotFoundException("User not found");
        }
        return user;
    }

}