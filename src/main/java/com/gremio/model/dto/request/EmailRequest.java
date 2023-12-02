package com.gremio.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

// Create a DTO (Data Transfer Object) class for the request body
@RequiredArgsConstructor
@AllArgsConstructor
public class EmailRequest {
    private String email;
    
    
    // Add getters and setters
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
}