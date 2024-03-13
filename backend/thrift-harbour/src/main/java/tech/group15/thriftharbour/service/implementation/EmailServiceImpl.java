package tech.group15.thriftharbour.service.implementation;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import tech.group15.thriftharbour.service.EmailService;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
  private final JavaMailSender mailSender;

  /**
   * Sends an email to the specified user with the given subject and a message containing the provided URL.
   *
   * @param to The email address of the recipient.
   * @param subject The subject of the email.
   * @param url The URL of reset password page.
   */
  public void sendEmail(String to, String subject, String url) {

    try {
      MimeMessage message = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message);
      helper.setFrom("thriftharbour15@gmail.com");

      helper.setTo(to);

      String content =
          "<p>Hello,</p>"
              + "<p>You have requested to reset your password.</p>"
              + "<p>Click the link below to change your password:</p>"
              + "<p><a href=\""
              + url
              + "\">Reset Password</a></p>"
              + "<br>";

      helper.setSubject(subject);

      helper.setText(content, true);

      mailSender.send(message);
    } catch (MessagingException e) {
      throw new MailSendException(String.valueOf(e));
    }
  }
}
