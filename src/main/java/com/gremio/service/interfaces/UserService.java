package com.gremio.service.interfaces;

import com.gremio.model.dto.UserDetailsDto;
import com.gremio.model.dto.UserDto;
import com.gremio.persistence.entity.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    /**
     * Finds the user with the given email.
     *
     * @param email The email address of the user to find.
     * @return The user with the provided email, or null if not found.
     */
    User findUserByEmail(String email);

    /**
     * Finds the user with the given ID and converts it to UserDetailsDto.
     *
     * @param id The ID of the user to find.
     * @return An optional containing UserDetailsDto if the user is found, empty otherwise.
     */
    Optional<UserDetailsDto> findById(Long id);

    /**
     * Creates a new user in the system.
     *
     * @param user The user object to create.
     * @return The newly created user.
     */
    User create(User user);

    /**
     * Saves the user in the system.
     *
     * @param user The user object to save.
     */
    void save(User user);

    /**
     * Retrieves a page of users with UserDetailsDto format.
     *
     * @param pageable The pagination information.
     * @return A page of UserDetailsDto objects.
     */
    Page<UserDetailsDto> getAllUser(Pageable pageable);

    /**
     * update user data.
     *
     * @param id of a user
     * @param userDto the update requested userDto
     * @return the updated user
     */
    User update(long id, UserDto userDto);
}