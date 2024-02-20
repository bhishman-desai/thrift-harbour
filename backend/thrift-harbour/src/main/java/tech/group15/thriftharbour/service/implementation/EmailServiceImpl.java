package tech.group15.thriftharbour.service.implementation;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import tech.group15.thriftharbour.service.EmailService;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String url) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setFrom("thriftharbour15@gmail.com");
//        message.setSubject(subject);
//        message.setText(body);
//
//        mailSender.send(message);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom("thriftharbour15@gmail.com");

            helper.setTo(to);

            String content = "<p>Hello,</p>"
                    + "<p>You have requested to reset your password.</p>"
                    + "<p>Click the link below to change your password:</p>"
                    + "<p><a href=\"" + url + "\">Reset Password</a></p>"
                    + "<br>";

            helper.setSubject(subject);

            helper.setText(content, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
