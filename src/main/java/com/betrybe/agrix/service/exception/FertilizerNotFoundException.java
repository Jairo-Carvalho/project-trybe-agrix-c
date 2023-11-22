package com.betrybe.agrix.service.exception;

/**
 * The type Fertilizer not found exception.
 */
public class FertilizerNotFoundException extends RuntimeException {
  public FertilizerNotFoundException(String message) {
    super(message);
  }
}
