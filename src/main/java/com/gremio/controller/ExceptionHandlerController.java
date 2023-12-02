package com.gremio.controller;

import com.gremio.enums.GeneralExceptionMessage;
import com.gremio.exception.NotFoundException;
import com.gremio.model.dto.response.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    /**
     * Handles the {@link NotFoundException} and returns an appropriate HTTP response.
     *
     * @param exception The NotFoundException instance.
     * @return A ResponseEntity with HTTP status 404 (Not Found) and an ExceptionResponse body.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> notFoundExceptionHandler(final NotFoundException exception) {
        log.info("Not found exception: {}", exception.getMessage());
        return createExceptionResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    /**
     * Creates an ExceptionResponse entity with the specified HTTP status and error message.
     *
     * @param status  The HTTP status to set in the response entity.
     * @param message The error message to include in the ExceptionResponse body.
     * @return A ResponseEntity with the specified HTTP status and an ExceptionResponse body.
     */
    private ResponseEntity<ExceptionResponse> createExceptionResponse(final HttpStatus status, final String message) {
        return ResponseEntity.status(status).body(new ExceptionResponse(message));
    }

    /**
     * Exception handler method to handle general exceptions and runtime exceptions.
     * It logs the exception and returns a ResponseEntity with an {@link ExceptionResponse}
     * containing the HTTP status code and the exception message.
     *
     * @param exception The exception to be handled.
     * @return A ResponseEntity with an {@link ExceptionResponse}.
     *
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<ExceptionResponse> generalExceptionHandler(final Exception exception) {
        log.error("Not caught exception", exception);
        
        final String exceptionMessage = exception instanceof RuntimeException
            ? exception.getMessage()
            : GeneralExceptionMessage.INTERNAL_SERVER_ERROR.getKey();
        
        return createExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, exceptionMessage);
    }
    

}