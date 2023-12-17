package com.gremio.model.dto.request;

import com.gremio.validator.annotation.Required;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
public class EmailRequest {
    @Required
    private String email;
    
    
    // Add getters and setters
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
}