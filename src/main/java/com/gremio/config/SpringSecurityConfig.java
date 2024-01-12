package com.gremio.config;

import com.gremio.jwt.archive.AuthenticationProcessingFilter;
import com.gremio.jwt.JwtAuthTokenFilter;
import com.gremio.service.interfaces.JwtService;
import com.gremio.service.interfaces.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
@EnableJpaAuditing
public class SpringSecurityConfig {
    private static final String[] AUTH_WHITELIST = {
        "/api/auth/**",
        "/v3/api-docs",
        "/login/**",
        "/users/forgot-password",
        "/users/reset-password",
        "/graphql/**",
        "/graphiql"
    };

    private final PasswordEncoder passwordEncoder;
    private final ConversionService conversionService;
    private final JwtService jwtService;
    private final UserService userService;
    private final AuthenticationConfiguration authConfig;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(
            final AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
       return http
           .cors(AbstractHttpConfigurer::disable)
           .authorizeHttpRequests(auth ->
               auth.requestMatchers(AUTH_WHITELIST)
                   .permitAll()
                   .anyRequest()
                   .authenticated())
               .addFilterBefore(new AuthenticationProcessingFilter(authenticationManager(authConfig),
                   conversionService, jwtService, userService), UsernamePasswordAuthenticationFilter.class)
               .addFilter(new JwtAuthTokenFilter(authenticationManager(authConfig), jwtService, userService))
               .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .exceptionHandling(exception ->
                   exception.authenticationEntryPoint((httpServletRequest, httpServletResponse, e) ->
                       httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "unauthorized"))
                        .accessDeniedHandler(new AccessDeniedHandlerImpl()))
               .logout(LogoutConfigurer::permitAll)
               .csrf(AbstractHttpConfigurer::disable)
               .build();
    }
}