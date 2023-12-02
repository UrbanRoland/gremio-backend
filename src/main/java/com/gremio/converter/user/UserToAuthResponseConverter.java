package com.gremio.converter.user;

import com.gremio.model.dto.response.AuthResponse;
import com.gremio.persistence.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToAuthResponseConverter implements Converter<User, AuthResponse> {

    @Override
    public AuthResponse convert(final User source) {
        return AuthResponse
                .builder()
                .id(source.getId())
                .email(source.getEmail())
                .role(source.getRole())
                .refreshToken(source.getRefreshToken())
                .build();
    }
}