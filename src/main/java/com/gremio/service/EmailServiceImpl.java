package com.gremio.service;

import com.gremio.config.EmailPropertyConfig;
import com.gremio.config.RedisConfig;
import com.gremio.service.interfaces.EmailService;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisConfig.class);
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final EmailPropertyConfig emailPropertyConfig;

    /**
     * {@inheritDoc}
     */
    @Override
    public void forgotPasswordEmail(final String to, final  String subject,
                                    final  String templateName, final String token, final String username) {

        final HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        final UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));

        final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        final Context context = createContext(userAgent, username, token);

        try {
            setMailContent(to, subject, templateName, helper, context);
            javaMailSender.send(mimeMessage);
        } catch (final MessagingException e) {
            LOGGER.error("Error while sending mail .. {}", e.getMessage());
        }
    }

    /**
     * Create a Thymeleaf Context object for generating email content.
     *
     * @param userAgent The user agent representing the client's browser and operating system.
     * @param username The username of the recipient.
     * @param token The token used for password reset or other actions.
     * @return The Thymeleaf Context object populated with variables for email content generation.
     */
    private Context createContext(final UserAgent userAgent, final String username, final String token) {
        final Context context = new Context();
        context.setVariable("product", "Gremio");
        context.setVariable("username", username);
        context.setVariable("operating_system", userAgent.getOperatingSystem().getName());
        context.setVariable("browser_name", userAgent.getBrowser().getName());
        context.setVariable("action_url", emailPropertyConfig.getForgotPasswordPrefix() + token);
        return context;
    }

    /**
     * Set mail content for sending an email.
     *
     * @param to The recipient's email address.
     * @param subject The subject of the email.
     * @param templateName The name of the Thymeleaf template used for email content.
     * @param helper The MimeMessageHelper object used for creating and configuring the MimeMessage.
     * @param context The Thymeleaf Context object containing variables to be used in the template.
     * @throws MessagingException If an error occurs while setting the mail content.
     */
    private void setMailContent(final String to, final String subject,
                                final String templateName, final MimeMessageHelper helper,
                                final Context context) throws MessagingException {
        helper.setTo(to);
        helper.setSubject(subject);
        final String htmlContent = templateEngine.process(templateName, context);
        helper.setText(htmlContent, true);
    }

}