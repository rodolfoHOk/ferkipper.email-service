package br.com.hiokdev.emailservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;

import br.com.hiokdev.emailservice.adapters.EmailSenderGateway;
import br.com.hiokdev.emailservice.infra.fake.FakeMailSender;
import br.com.hiokdev.emailservice.infra.sendgrid.SendGridEmailSender;
import br.com.hiokdev.emailservice.infra.ses.SesEmailSender;

@Configuration
public class EmailSenderConfig {
  
  @Value(value = "${mail.provider}")
  private String emailProvider;

  @Autowired
  private JavaMailSender javaMailSender;

  // @Bean
  // public AmazonSimpleEmailService amazonSimpleEmailService() {
  //   return AmazonSimpleEmailServiceClientBuilder.standard().build();
  // }

  @Bean
  public EmailSenderGateway emailSenderGateway() {
    if (emailProvider.equals("sendgrid")) {
      return new SendGridEmailSender(javaMailSender);
    } else if (emailProvider.equals("aws-ses")) {
      // return new SesEmailSender(amazonSimpleEmailService());
      return new FakeMailSender();
    }
    return new FakeMailSender();
  }

}
