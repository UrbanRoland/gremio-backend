package com.gremio.converter.user;

import com.gremio.model.dto.UserDto;
import com.gremio.model.dto.request.UpdateUserRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UpdateUserRequestToUserDto implements Converter<UpdateUserRequest, UserDto> {

    /**
     * Convert UpdateUserRequest object into User object.
     *
     * @param source UpdateUserRequest
     * @return object UserDto
     */
    @Override
    public UserDto convert(final UpdateUserRequest source) {
        return UserDto.builder()
            .email(source.getEmail())
            .password(source.getPassword())
            .role(source.getRole())
            .build();
    }
}