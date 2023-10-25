package br.com.hiokdev.emailservice.infra.ses;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

import br.com.hiokdev.emailservice.core.EmailRequest;
import br.com.hiokdev.emailservice.core.exceptions.EmailServiceException;

public class SesEmailSenderTest {

  @Mock
  private AmazonSimpleEmailService amazonSimpleEmailService;

  @InjectMocks
  private SesEmailSender sesEmailSender;

  @Test
  void testSendEmail() {
    EmailRequest email = new EmailRequest(
      "test@email.com",
      "test email subject",
      "test email body"
    );
    SendEmailRequest request = new SendEmailRequest()
      .withSource("hioktec@gmail.com")
      .withDestination(new Destination().withToAddresses(email.to()))
      .withMessage(new Message()
        .withSubject(new Content(email.subject()))
        .withBody(new Body().withText(new Content(email.body())))
      );
    amazonSimpleEmailService = AmazonSimpleEmailServiceClientBuilder.standard().build();
    Mockito.doNothing().when(amazonSimpleEmailService).sendEmail(request);
    
    sesEmailSender.sendEmail(email.to(), email.subject(), email.body());

    verify(amazonSimpleEmailService).sendEmail(request);
  }

  @Test
  void testErrorSendEmail() {
    EmailRequest email = new EmailRequest(
      "test@email.com",
      "test email subject",
      "test email body"
    );
    SendEmailRequest request = new SendEmailRequest()
      .withSource("hioktec@gmail.com")
      .withDestination(new Destination().withToAddresses(email.to()))
      .withMessage(new Message()
        .withSubject(new Content(email.subject()))
        .withBody(new Body().withText(new Content(email.body())))
      );

    amazonSimpleEmailService = AmazonSimpleEmailServiceClientBuilder.standard().build();
    Mockito.doThrow(EmailServiceException.class).when(amazonSimpleEmailService).sendEmail(request);

    Assertions.assertThrows(
      EmailServiceException.class,
      () -> sesEmailSender.sendEmail(email.to(), email.subject(), email.body()),
      "Failure while sending email"
    );
  }
  
}
