package com.betrybe.agrix.dto;

import com.betrybe.agrix.repository.Crop;
import com.betrybe.agrix.repository.Farm;
import java.time.LocalDate;

/**
 * The type Crop dto.
 */
public record CropDto(
    Integer id,
    String name,
    Double plantedArea,
    Integer farmId,
    LocalDate plantedDate,
    LocalDate harvestDate

) {

  /**
   * Crop to dto crop dto.
   *
   * @param crop the crop
   * @return the crop dto
   */
  public static CropDto cropToDto(Crop crop) {
    return new CropDto(
        crop.getId(),
        crop.getName(),
        crop.getPlantedArea(),
        crop.getFarm().getId(),
        crop.getPlantedDate(),
        crop.getHarvestDate()
    );
  }

  /**
   * To entity crop.
   *
   * @param farm the farm
   * @return the crop
   */
  public Crop toEntity(Farm farm) {
    Crop crop = new Crop();
    crop.setFarm(farm);
    crop.setName(name);
    crop.setPlantedArea(plantedArea);
    crop.setPlantedDate(plantedDate);
    crop.setHarvestDate(harvestDate);

    return crop;
  }
}
