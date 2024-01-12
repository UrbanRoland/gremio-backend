package com.gremio.controller;

import com.gremio.exception.NotFoundException;
import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    /**
     * Handle all exceptions thrown by the GraphQL API.
     *
     * @param ex  The exception.
     * @param env The data fetching environment.
     * @return A GraphQLError object.
     */
    @GraphQlExceptionHandler
    public GraphQLError handleException(final RuntimeException ex, final DataFetchingEnvironment env) {
        log.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage());

        final ErrorType errorType = determineErrorType(ex);

        return GraphQLError.newError()
            .errorType(errorType)
            .message(ex.getMessage())
            .path(env.getExecutionStepInfo().getPath())
            .location(env.getField().getSourceLocation())
            .build();
    }

    private ErrorType determineErrorType(final RuntimeException ex) {
        if (ex instanceof NotFoundException) {
            return ErrorType.NOT_FOUND;
        }

        return ErrorType.INTERNAL_ERROR;
    }
}