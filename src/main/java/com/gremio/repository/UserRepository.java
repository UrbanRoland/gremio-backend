package com.gremio.repository;

import com.gremio.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>, JpaRepository<User, Long> {

    /**
     * Finds the user with the given email.
     *
     * @param email The email address of the user to find.
     * @return The user with the provided email, or null if not found.
     */
    User findUserByEmail(String email);
}