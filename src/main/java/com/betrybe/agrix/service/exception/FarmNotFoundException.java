package com.betrybe.agrix.service.exception;

/**
 * The type Farm not found exception.
 */
public class FarmNotFoundException extends RuntimeException {
  public FarmNotFoundException(String message) {
    super(message);
  }
}
