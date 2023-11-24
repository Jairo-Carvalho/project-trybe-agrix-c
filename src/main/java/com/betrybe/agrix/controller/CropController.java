package com.betrybe.agrix.controller;

import com.betrybe.agrix.dto.CropDto;
import com.betrybe.agrix.dto.FertilizerDto;
import com.betrybe.agrix.repository.Crop;
import com.betrybe.agrix.repository.Farm;
import com.betrybe.agrix.service.CropService;
import com.betrybe.agrix.service.FarmService;
import com.betrybe.agrix.service.exception.CropNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Crop controller.
 */
@RestController
@RequestMapping
public class CropController {
  @Autowired
  public CropService cropService;

  @Autowired
  public FarmService farmService;

  /**
   * Gets crops by farm id.
   *
   * @param farmId the farm id
   * @return the crops by farm id
   */
  @GetMapping("/farms/{farmId}/crops")
  public ResponseEntity<?> getCropsByFarmId(@PathVariable Integer farmId) {

    Optional<Farm> farm = farmService.getFarmById(farmId);

    if (farm.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fazenda não encontrada!");
    }

    List<Crop> crops = farm.get().getCrops();

    List<CropDto> cropDtos = crops.stream()
        .map(crop -> new CropDto(
            crop.getId(),
            crop.getName(),
            crop.getPlantedArea(),
            crop.getFarm().getId(),
            crop.getPlantedDate(),
            crop.getHarvestDate()
        ))
        .toList();
    System.out.println(crops);


    return ResponseEntity.status(HttpStatus.OK).body(cropDtos);
  }

  /**
   * Gets all crops.
   *
   * @return the all crops
   */
  @GetMapping("/crops")
  @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    public List<CropDto> getAllCrops() {
    return cropService.getAllCrops().stream()
        .map(crop -> CropDto.cropToDto(crop))
        .toList();
  }

  /**
   * Gets crop by id.
   *
   * @param id the id
   * @return the crop by id
   */
  @GetMapping("/crops/{id}")
  public ResponseEntity<Object> getCropById(@PathVariable Integer id) {
    Optional<Crop> crop = cropService.getCropById(id);

    if (crop.isPresent()) {
      CropDto cropDto = CropDto.cropToDto(crop.get());
      return ResponseEntity.status(HttpStatus.OK).body(cropDto);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plantação não encontrada!");
    }
  }

  @GetMapping("/crops/search")
  public ResponseEntity<List<CropDto>> getCropsByHarvestDate(
      @RequestParam LocalDate start,
      @RequestParam LocalDate end
  ) {
    List<CropDto> crops = cropService.getCropsByHarvestDate(start, end);
    return ResponseEntity.status(HttpStatus.OK).body(crops);
  }

  /**
   * Associate crop with fertilizer response entity.
   *
   * @param cropId       the crop id
   * @param fertilizerId the fertilizer id
   * @return the response entity
   */
  @PostMapping("/crops/{cropId}/fertilizers/{fertilizerId}")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<String> associateCropWithFertilizer(
      @PathVariable Integer cropId,
      @PathVariable Integer fertilizerId
  ) {
    cropService.associateCropWithFertilizer(cropId, fertilizerId);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body("Fertilizante e plantação associados com sucesso!");
  }

  /**
   * Gets fertilizers by crop id.
   *
   * @param cropId the crop id
   * @return the fertilizers by crop id
   */
  @GetMapping("/crops/{cropId}/fertilizers")
  public ResponseEntity<Object> getFertilizersByCropId(@PathVariable Integer cropId) {
    try {
      List<FertilizerDto> fertilizers = cropService.getFertilizersByCropId(cropId);
      return ResponseEntity.status(HttpStatus.OK).body(fertilizers);
    } catch (CropNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }
}
