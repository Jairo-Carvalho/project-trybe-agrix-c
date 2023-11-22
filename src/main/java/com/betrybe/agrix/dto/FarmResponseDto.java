package com.betrybe.agrix.dto;

/**
 * The type Farm response dto.
 *
 * @param <T> the type parameter
 */
public record FarmResponseDto<T>(String message, T data) {

}
