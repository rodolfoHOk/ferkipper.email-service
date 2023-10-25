package br.com.hiokdev.emailservice.infra.fake;

import br.com.hiokdev.emailservice.adapters.EmailSenderGateway;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeMailSender implements EmailSenderGateway{

  @Override
  public void sendEmail(String to, String subject, String body) {
    log.info(
      "Email Sended: {to:  " + to + " subject: " + subject + " body: " + body + "}"
    );
  }
  
}
