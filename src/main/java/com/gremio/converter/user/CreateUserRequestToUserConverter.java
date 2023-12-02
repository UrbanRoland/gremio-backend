package com.gremio.converter.user;

import com.gremio.model.dto.request.CreateUserRequest;
import com.gremio.persistence.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CreateUserRequestToUserConverter implements Converter<CreateUserRequest, User> {

    @Override
    public User convert(final CreateUserRequest source) {
        return User
                .builder()
                .email(source.getEmail())
                .role(source.getRole())
                .password(source.getPassword())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .build();
    }
}