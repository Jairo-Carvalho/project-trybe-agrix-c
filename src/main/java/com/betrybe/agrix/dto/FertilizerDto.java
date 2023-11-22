package com.betrybe.agrix.dto;

import com.betrybe.agrix.repository.Fertilizer;

/**
 * The type Fertilizer dto.
 */
public record FertilizerDto(Integer id, String name, String brand, String composition) {

  /**
   * Fertilizer to dto fertilizer dto.
   *
   * @param fertilizer the fertilizer
   * @return the fertilizer dto
   */
  public static FertilizerDto fertilizerToDto(Fertilizer fertilizer) {
    return new FertilizerDto(
        fertilizer.getId(),
        fertilizer.getName(),
        fertilizer.getBrand(),
        fertilizer.getComposition()
    );
  }

  /**
   * To entity fertilizer.
   *
   * @return the fertilizer
   */
  public Fertilizer toEntity() {
    Fertilizer fertilizer = new Fertilizer();
    fertilizer.setName(name);
    fertilizer.setBrand(brand);
    fertilizer.setComposition(composition);

    return fertilizer;
  }
}
