package com.gremio.model.dto.response;

import com.gremio.message.MessageKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {

    private String message;
    public ExceptionResponse(final MessageKey exceptionMessageKey) {
        this(exceptionMessageKey.getKey());
    }
}