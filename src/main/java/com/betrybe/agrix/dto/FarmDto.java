package com.betrybe.agrix.dto;

import com.betrybe.agrix.repository.Farm;

/**
 * The type Farm dto.
 */
public record FarmDto(Integer id, String name, Double size) {

  public Farm toFarm() {
    return new Farm(id, name, size);
  }
}
