package br.com.hiokdev.emailservice.core;

import io.swagger.v3.oas.annotations.media.Schema;

public record EmailRequest(
  @Schema(example = "john.doe@email.com")
  String to,

  @Schema(example = "test mail subject")
  String subject,
  
  @Schema(example = "test mail body")
  String body
) {
  
}
