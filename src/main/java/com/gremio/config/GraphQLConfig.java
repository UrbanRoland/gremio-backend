package com.gremio.config;

import com.gremio.directive.AuthorisationDirective;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQLConfig {

    @Bean
    public GraphQLScalarType localDateTimeScalar() {
        return ExtendedScalars.DateTime;
    }

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder
            .scalar(localDateTimeScalar())
            .directive("auth", new AuthorisationDirective());
    }
}