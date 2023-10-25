package br.com.hiokdev.emailservice.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hiokdev.emailservice.adapters.EmailSenderGateway;
import br.com.hiokdev.emailservice.core.EmailSenderUseCase;

@Service
public class EmailSenderService implements EmailSenderUseCase {

  private final EmailSenderGateway emailSenderGateway;

  @Autowired  
  public EmailSenderService(EmailSenderGateway emailSenderGateway) {
    this.emailSenderGateway = emailSenderGateway;
  }

  @Override
  public void sendEmail(String to, String subject, String body) {
    this.emailSenderGateway.sendEmail(to, subject, body);
  }
  
}
