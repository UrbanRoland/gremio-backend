package com.gremio.converter.user;

import com.gremio.model.dto.response.archive.UserDetailsDto;
import com.gremio.persistence.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converts a {@link User} to a {@link UserDetailsDto}.
 */
@Component
public class UserToUserDetailsDtoConverter implements Converter<User, UserDetailsDto> {

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetailsDto convert(final User source) {
        return UserDetailsDto
                .builder()
                .email(source.getEmail())
                .role(source.getRole())
                .build();
    }
}