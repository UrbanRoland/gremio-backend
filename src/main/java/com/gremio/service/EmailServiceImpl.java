package com.gremio.service;

import com.gremio.service.interfaces.EmailService;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
   // @Value("${changed.link}")
   // private String changedLink;

    /**
     * {@inheritDoc}
     */
    @Override
    public void forgotPasswordEmail(final String to, final  String subject, final  String templateName, final String token, final String username) {
        //todo user if exists call userservice
    
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
    
    
        System.out.println("  ads " + userAgent.getOperatingSystem().getName() + userAgent.getBrowser().getName());
        final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        final Context context = new Context();
        context.setVariable("product","Gremio");
        context.setVariable("username", username);
        context.setVariable("operating_system", userAgent.getOperatingSystem().getName());
        context.setVariable("browser_name", userAgent.getBrowser().getName());
        context.setVariable("action_url", "http://localhost:4200/reset-password/" + token);
        
        try {
            helper.setTo(to);
            helper.setSubject(subject);
            final String htmlContent = templateEngine.process(templateName, context);
            helper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            // Handle exception
        }
    }


}