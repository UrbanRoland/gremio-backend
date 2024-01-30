package com.gremio.converter.user;

import com.gremio.model.input.UserInput;
import com.gremio.persistence.domain.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converts a {@link UserInput} to a {@link User}.
 */
@Component
public class UserInputToUserConverter implements Converter<UserInput, User> {

    /**
     * {@inheritDoc}
     */
    @Override
    public User convert(final UserInput source) {
        return User.builder()
            .email(source.email() == null ? null : source.email())
            .role(source.role() == null ? null : source.role())
            .password(source.password() == null ? null : source.password())
            .firstName(source.firstName() == null ? null : source.firstName())
            .lastName(source.lastName() == null ? null : source.lastName())
            .build();
    }
}