package br.com.hiokdev.emailservice.infra.sendgrid;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import br.com.hiokdev.emailservice.core.EmailRequest;
import br.com.hiokdev.emailservice.core.exceptions.EmailServiceException;

@ExtendWith(MockitoExtension.class)
@ExtendWith(OutputCaptureExtension.class)
public class SendGridEmailSenderTest {

  @Mock
  private JavaMailSender javaMailSender;
  
  @InjectMocks
  private SendGridEmailSender sendGridEmailSender;

  @Test
  void testSendEmail(CapturedOutput output) {
    EmailRequest request = new EmailRequest(
      "test@email.com",
      "test email subject",
      "test email body"
    );
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("testes.dev.hiok@gmail.com");
    message.setTo(request.to());
    message.setSubject(request.subject());
    message.setText(request.body());
    String expectLogMessage = "sending email to: " + request.to();

    sendGridEmailSender.sendEmail(request.to(), request.subject(), request.body());
    
    verify(javaMailSender).send(message);
    Assertions.assertTrue(output.getOut().contains(expectLogMessage));
  }

  @Test
  void testErrorSendEmail() {
    EmailRequest request = new EmailRequest(
      "test@email.com",
      "test email subject",
      "test email body"
    );
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("testes.dev.hiok@gmail.com");
    message.setTo(request.to());
    message.setSubject(request.subject());
    message.setText(request.body());

    Mockito.doThrow(EmailServiceException.class).when(javaMailSender).send(message);

    Assertions.assertThrows(
      EmailServiceException.class,
      () -> sendGridEmailSender.sendEmail(request.to(), request.subject(), request.body()),
      "Failure while sending email"
    );
  }
  
}
