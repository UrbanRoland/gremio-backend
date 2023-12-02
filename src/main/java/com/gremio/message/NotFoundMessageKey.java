package com.gremio.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotFoundMessageKey implements MessageKey {

    /**
     * Message key for User not found.
     */
    USER("not.found.user");
    private final String key;
}