package br.com.hiokdev.emailservice.controllers;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import br.com.hiokdev.emailservice.core.EmailRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class EmailSenderControllerIT {

  @LocalServerPort
  private int port;

  @BeforeEach
  public void setup() {
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    RestAssured.port = this.port;
    RestAssured.basePath = "/api/email";
  }

  @Test
  void testSendEmail() {
    EmailRequest request = new EmailRequest(
      "test@email.com",
      "test email subject",
      "test email body"
    );

    RestAssured
      .given().contentType(ContentType.JSON).accept(ContentType.JSON).body(request)
      .when().post()
      .then().statusCode(HttpStatus.SC_OK);
  }

    @Test
  void testErrorSendEmail() {
    String emailRequest = "";
    RestAssured
      .given().contentType(ContentType.JSON).accept(ContentType.JSON).body(emailRequest)
      .when().post()
      .then().statusCode(HttpStatus.SC_BAD_REQUEST);
  }
  
}
