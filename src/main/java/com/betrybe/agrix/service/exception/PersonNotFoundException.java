package com.betrybe.agrix.service.exception;

/**
 * The type Person not found exception.
 */
public class PersonNotFoundException extends RuntimeException {
  public PersonNotFoundException(String message) {
    super(message);
  }
}
