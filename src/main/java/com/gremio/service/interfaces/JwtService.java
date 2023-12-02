package com.gremio.service.interfaces;

import com.gremio.model.dto.response.AuthResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    /**
     * Extracts the username from the given JWT token.
     *
     * @param token The JWT token from which to extract the username.
     * @return The username extracted from the token.
     */
    String extractUsername(String token);

    /**
     * Generates a JWT token for the provided user details.
     *
     * @param userDetails The user details for whom to generate the token.
     * @return The generated JWT token.
     */
    String generateToken(UserDetails userDetails);

    /**
     * Validates if the given JWT token is valid for the provided user details.
     *
     * @param token        The JWT token to validate.
     * @param userDetails  The user details for whom to validate the token.
     * @return True if the token is valid for the given user; otherwise, false.
     */
    boolean isTokenValid(String token, UserDetails userDetails);

    /**
     * Refreshes the authentication token for the given refresh token.
     *
     * @param token The refresh token to use for refreshing the authentication token.
     * @return An {@link AuthResponse} containing the new access token, or null if the user does not exist.
     */
    AuthResponse refreshAuthToken(String token);

    /**
     * Generates a refresh token with the provided extra claims for the given user details.
     *
     * @param userDetails   The user details for whom to generate the refresh token.
     * @return The generated refresh token.
     */
    String generateRefreshToken(UserDetails userDetails);
}