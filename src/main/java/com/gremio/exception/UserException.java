package com.gremio.exception;

import com.gremio.enums.UserMessageKey;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -316161373670197918L;

    public UserException(final @NotNull UserMessageKey messageKey) {
        super(messageKey.getKey());
    }
}