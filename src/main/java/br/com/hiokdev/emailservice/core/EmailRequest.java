package br.com.hiokdev.emailservice.core;

public record EmailRequest(
  String to,
  String subject,
  String body
) {
  
}
