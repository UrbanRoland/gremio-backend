package com.gremio.controller;

import com.gremio.service.interfaces.EmailService;
import lombok.NoArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

@RestController
@RequestMapping("/email")
public class EmailController extends AbstractController {
    private final EmailService emailService;
    
    public EmailController(final ConversionService conversionService, final EmailService emailService) {
        super(conversionService);
        this.emailService = emailService;
    }

    @PostMapping("/forgot-password")
    public String sendEmail() {


        //emailService.forgotPasswordEmail("urolir@gmail.com","test5","resetPassword");
        return "Email sent successfully!";
    }
}