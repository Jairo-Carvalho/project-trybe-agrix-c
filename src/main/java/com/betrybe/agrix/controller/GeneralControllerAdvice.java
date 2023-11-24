package com.betrybe.agrix.controller;

import com.betrybe.agrix.service.exception.CropNotFoundException;
import com.betrybe.agrix.service.exception.FarmNotFoundException;
import com.betrybe.agrix.service.exception.FertilizerNotFoundException;
import com.betrybe.agrix.service.exception.PersonNotFoundException;
import com.betrybe.agrix.service.exception.UsernameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * The type General controller advice.
 */
@ControllerAdvice
public class GeneralControllerAdvice {
  @ExceptionHandler({
      FarmNotFoundException.class,
      FertilizerNotFoundException.class,
      CropNotFoundException.class,
      PersonNotFoundException.class,
      UsernameNotFoundException.class
  })

  public ResponseEntity<String> handlerNotFound(RuntimeException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
  }

}
