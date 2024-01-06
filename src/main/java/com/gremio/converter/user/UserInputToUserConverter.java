package com.gremio.converter.user;

import com.gremio.model.dto.UserInput;
import com.gremio.persistence.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserInputToUserConverter implements Converter<UserInput, User> {

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