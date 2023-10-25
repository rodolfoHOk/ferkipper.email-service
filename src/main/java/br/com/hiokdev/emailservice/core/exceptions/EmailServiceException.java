package br.com.hiokdev.emailservice.core.exceptions;

public class EmailServiceException extends RuntimeException {

  public EmailServiceException(String message, Exception cause) {
    super(message, cause);
  }
  
}
