package br.com.hiokdev.emailservice.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
public class OpenApiConfig {
  

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
      .info(new Info()
        .title("Email service")
        .description("Desafio backend Uber email service")
        .version("v1")
        .contact(new Contact().name("Rudolf HiOk").email("hioktec@gmail.com"))
      ).tags(Arrays.asList(
        new Tag().name("Email").description("Send Email")
      ));
  }
}
