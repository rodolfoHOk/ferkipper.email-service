package br.com.hiokdev.emailservice.infra.sendgrid;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import br.com.hiokdev.emailservice.adapters.EmailSenderGateway;
import br.com.hiokdev.emailservice.core.exceptions.EmailServiceException;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class SendGridEmailSender implements EmailSenderGateway {

  private final JavaMailSender javaMailSender;

  public SendGridEmailSender(JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }

  @Override
  public void sendEmail(String to, String subject, String body) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("testes.dev.hiok@gmail.com");
    message.setTo(to);
    message.setSubject(subject);
    message.setText(body);

    try {
      javaMailSender.send(message);
      log.info("sending email to: " + to);
    } catch (MailException exception) {
      log.error(exception.getMessage());
      throw new EmailServiceException("Failure while sending email", exception);
    }
  }
  
}
