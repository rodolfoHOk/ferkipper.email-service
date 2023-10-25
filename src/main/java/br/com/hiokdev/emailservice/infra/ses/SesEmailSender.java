package br.com.hiokdev.emailservice.infra.ses;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.AmazonSimpleEmailServiceException;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

import br.com.hiokdev.emailservice.adapters.EmailSenderGateway;
import br.com.hiokdev.emailservice.core.exceptions.EmailServiceException;

public class SesEmailSender implements EmailSenderGateway {

  private final AmazonSimpleEmailService amazonSimpleEmailService;

  public SesEmailSender(AmazonSimpleEmailService amazonSimpleEmailService) {
    this.amazonSimpleEmailService = amazonSimpleEmailService;
  }

  @Override
  public void sendEmail(String to, String subject, String body) {
    SendEmailRequest request = new SendEmailRequest()
      .withSource("hioktec@gmail.com")
      .withDestination(new Destination().withToAddresses(to))
      .withMessage(new Message()
        .withSubject(new Content(subject))
        .withBody(new Body().withText(new Content(body)))
      );

    try {
      this.amazonSimpleEmailService.sendEmail(request);
    } catch (AmazonSimpleEmailServiceException exception) {
      throw new EmailServiceException("Failure when sending email", exception);
    }
  }
  
}
