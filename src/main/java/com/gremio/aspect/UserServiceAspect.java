package com.gremio.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class UserServiceAspect {

   private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceAspect.class);
    
    @Pointcut("execution(public * com.gremio.service.UserServiceImpl.loadUserByUsername(..)) && args(email))")
    private void loadUserByUsernameExecution(String email) {}
    
    
    /**
     * Logs the attempt to load a user by username.
     *
     * @param email The email address of the user to load.
     */
    @Before("loadUserByUsernameExecution(email)")
    public void logUserLoadingAttempt(String email) {
        LOGGER.info("Attempt to load user by username... {}", email);
    }
    
}