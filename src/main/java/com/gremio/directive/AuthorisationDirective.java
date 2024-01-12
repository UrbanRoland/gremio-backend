package com.gremio.directive;


import graphql.schema.DataFetcher;
import graphql.schema.FieldCoordinates;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLFieldsContainer;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;
import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthorisationDirective implements SchemaDirectiveWiring {

    /*
    * This method is called when the directive is used in the schema.
    *
     */
    @Override
    public GraphQLFieldDefinition onField(final SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {
        final String targetAuthRole = environment.getAppliedDirective().getArgument("role").getValue();

        final GraphQLFieldDefinition field = environment.getElement();
        final GraphQLFieldsContainer parentType = environment.getFieldsContainer();
        final FieldCoordinates coordinates = FieldCoordinates.coordinates(parentType, field);

        final DataFetcher<?> originalDataFetcher = environment.getCodeRegistry().getDataFetcher(coordinates, field);

        final DataFetcher<Object> authDataFetcher = dataFetchingEnvironment -> {
            final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (containsAuthority(authentication.getAuthorities(), targetAuthRole)) {
                return originalDataFetcher.get(dataFetchingEnvironment);
            } else {
                return null;
            }
        };

        environment.getCodeRegistry().dataFetcher(coordinates, authDataFetcher);
        return field;
    }

    private boolean containsAuthority(final Collection<? extends GrantedAuthority> authorities, final String targetAuthRole) {
        return authorities.stream()
            .anyMatch(authority -> authority.getAuthority().equals(targetAuthRole));
    }
}