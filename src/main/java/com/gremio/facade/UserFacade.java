package com.gremio.facade;

public interface UserFacade {
    String forgotPassword(String email);
    
    void resetPassword(String password, String token);
}