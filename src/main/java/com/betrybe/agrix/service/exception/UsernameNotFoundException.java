package com.betrybe.agrix.service.exception;

/**
 * The type Username not found exception.
 */
public class UsernameNotFoundException extends RuntimeException {
  public UsernameNotFoundException(String message) {
    super(message);
  }
}
