package com.betrybe.agrix.service.exception;

/**
 * The type Crop not found exception.
 */
public class CropNotFoundException extends RuntimeException {
  public CropNotFoundException(String message) {
    super(message);
  }
}
