package br.com.hiokdev.emailservice.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import br.com.hiokdev.emailservice.adapters.EmailSenderGateway;
import br.com.hiokdev.emailservice.core.EmailRequest;
import br.com.hiokdev.emailservice.infra.fake.FakeMailSender;

@ExtendWith(OutputCaptureExtension.class)
public class EmailSenderServiceTest {

  private EmailSenderGateway emailSenderGateway;

  private EmailSenderService emailSenderService;

  @Test
  void testSendEmail(CapturedOutput output) {
    EmailRequest request = new EmailRequest(
      "test@email.com",
      "test email subject",
      "test email body"
    );
    String expectLogString = "Email Sended: {to:  " + request.to() + " subject: " + request.subject() + " body: " + request.body() + "}";

    emailSenderGateway = new FakeMailSender();
    emailSenderService = new EmailSenderService(emailSenderGateway);
    emailSenderService.sendEmail(request.to(), request.subject(), request.body());

    Assertions.assertTrue(output.getOut().contains(expectLogString));
  }
  
}
