package com.gremio.facade;

import com.gremio.model.dto.UserInput;
import com.gremio.model.dto.request.CreateUserRequest;
import com.gremio.model.dto.response.AuthResponse;
import com.gremio.persistence.entity.User;

public interface UserFacade {

    /**
     * Sends a password reset email to the user with the given email address.
     *
     * @param email The email address of the user to send the password reset email to.
     * @return A string indicating the result of the operation.
     */

    String forgotPassword(String email);

    /**
     * Resets a user's password.
     *
     * @param password The new password.
     * @param token The password reset token.
     */
    void resetPassword(String password, String token);

    /**
     * Authenticates a user and returns an AuthResponse containing the access token and refresh token.
     *
     * @param email The email of the user to authenticate.
     * @param password The password of the user to authenticate.
     * @return An AuthResponse containing the access token and refresh token.
     */
    AuthResponse login(String email, String password);

    /**
     * Creates a new user in the system.
     *
     * @param user The CreateUserRequest containing the user details to create.
     * @return The UserDetailsDto of the newly created user.
     */
    User userRegistration(UserInput user);
}