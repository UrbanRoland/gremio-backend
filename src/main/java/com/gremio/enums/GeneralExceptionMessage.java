package com.gremio.enums;

import com.gremio.message.MessageKey;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GeneralExceptionMessage implements MessageKey {
    
    /**
     * Message key for error caused by bad request.
     */
    BAD_REQUEST("error.general.bad-request"),
    
    /**
     * Message key for uncaught error.
     */
    INVALID_ENUM_VALUE("error.general.invalid-enum-value"),
    
    /**
     * Message key for uncaught error.
     */
    INTERNAL_SERVER_ERROR("error.general.internal-server-error");
    
    private final String key;
}