package tech.group15.thriftharbour.service.implementation;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class EmailServiceImplTest<Session> {

    @Mock
    private JavaMailSender mailSender;



    @InjectMocks
    private EmailServiceImpl emailService;





    @Test
    public void testSendEmail_WithInvalidRecipient() {
        // Test sending email with invalid recipient
        assertThrows(Exception.class, () -> emailService.sendEmail("", "Test Subject", "https://example.com/reset"));
    }

    @Test
    public void testSendEmail_WithEmptySubject() {
        // Test sending email with empty subject
        assertThrows(Exception.class, () -> emailService.sendEmail("recipient@example.com", "", "https://example.com/reset"));
    }

    @Test
    public void testSendEmail_WithNullURL() {
        // Test sending email with null URL
        assertThrows(Exception.class, () -> emailService.sendEmail("recipient@example.com", "Test Subject", null));
    }





    @Test
    public void testSendEmail_MessagingExceptionHandling() throws MessagingException {
        // Create a JavaMailSenderImpl instance
        JavaMailSender mailSender = createMailSender();

        // Create an instance of your EmailServiceImpl and pass the mailSender
        EmailServiceImpl emailService = new EmailServiceImpl(mailSender);

        // Test sending email with MessagingException
        assertThrows(RuntimeException.class, () -> emailService.sendEmail("recipient@example.com", "Test Subject", "https://example.com/reset"));
    }

    private JavaMailSender createMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        // Configure mailSender properties
        mailSender.setHost("smtp.example.com");
        mailSender.setPort(25);
        mailSender.setUsername("your_username");
        mailSender.setPassword("your_password");
        return mailSender;
    }



    @Test
    public void testSendEmail_WithValidParameters() throws MessagingException {
        // Mocking the MimeMessage
        MimeMessage mimeMessage = mock(MimeMessage.class);
        // Mocking the behavior of mailSender.createMimeMessage() to return the mocked MimeMessage
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        // Mocking the behavior of mailSender.send() to do nothing
        doNothing().when(mailSender).send((MimeMessage) any());

        // Call the method under test
        String recipient = "recipient@example.com";
        String subject = "Test Subject";
        String url = "https://example.com/reset";
        emailService.sendEmail(recipient, subject, url);

        // Verify that createMimeMessage and send methods are called on the mock JavaMailSender
        verify(mailSender).createMimeMessage();
        verify(mailSender).send((MimeMessage) any());
    }


}

