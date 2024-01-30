package com.gremio.service.interfaces;

import com.gremio.model.input.UserInput;
import com.gremio.persistence.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import reactor.core.publisher.Mono;

public interface UserService extends UserDetailsService {

    /**
     * Finds the user with the given email.
     *
     * @param email The email address of the user to find.
     * @return The user with the provided email, or null if not found.
     */
    User findUserByEmail(String email);

    /**
     * Creates a new user in the system.
     *
     * @param user containing the user details to create.
     * @return The newly created user.
     */
    Mono<User> create(UserInput user);

    /**
     * Saves the user in the system.
     *
     * @param user The user object to save.
     */
    void save(User user);

    /**
     * update user data.
     *
     * @param userInput The user input.
     * @return the updated user
     */
    User update(UserInput userInput);
}