package br.com.hiokdev.emailservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.hiokdev.emailservice.application.EmailSenderService;
import br.com.hiokdev.emailservice.core.EmailRequest;
import br.com.hiokdev.emailservice.core.exceptions.EmailServiceException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Email")
@RestController
@RequestMapping("/api/email")
public class EmailSenderController {
  
  private final EmailSenderService emailSenderService;

  @Autowired
  public EmailSenderController(EmailSenderService emailSenderService) {
    this.emailSenderService = emailSenderService;
  }

  @Operation(summary = "Send email", responses = {
    @ApiResponse(responseCode = "200"),
    @ApiResponse(responseCode = "500")
  })
  @PostMapping
  public ResponseEntity<String> sendEmail(@RequestBody EmailRequest request) {
    try {
      this.emailSenderService.sendEmail(request.to(), request.subject(), request.body());
      return ResponseEntity
        .ok("Email sent successfully");
    } catch (EmailServiceException exception) {
      return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("Error while sending email");
    }
  }
  
}
