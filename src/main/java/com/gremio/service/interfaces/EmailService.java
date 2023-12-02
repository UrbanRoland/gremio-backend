package com.gremio.service.interfaces;

import org.thymeleaf.context.Context;

public interface EmailService {
    void forgotPasswordEmail(String to, String subject, String templateName, String token, String username);
}