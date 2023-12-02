package com.gremio.enums;

import com.gremio.message.MessageKey;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserMessageKey implements MessageKey {

    INVALID_TOKEN("user.invalid.token");
    
    private final String key;
}