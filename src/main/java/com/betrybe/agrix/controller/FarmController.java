package com.betrybe.agrix.controller;

import com.betrybe.agrix.dto.CropDto;
import com.betrybe.agrix.dto.FarmDto;
import com.betrybe.agrix.repository.Crop;
import com.betrybe.agrix.repository.Farm;
import com.betrybe.agrix.service.CropService;
import com.betrybe.agrix.service.FarmService;
import com.betrybe.agrix.service.exception.FarmNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Farm controller.
 */
@RestController
@RequestMapping("/farms")
public class FarmController {
  private final FarmService farmService;

  private final CropService cropService;

  /**
   * Instantiates a new Farm controller.
   *
   * @param farmService the farm service
   * @param cropService the crop service
   */
  @Autowired
  public FarmController(FarmService farmService, CropService cropService) {
    this.farmService = farmService;
    this.cropService = cropService;
  }

  /**
   * Create new farm response entity.
   *
   * @param newFarmDto the new farm dto
   * @return the response entity
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Farm> createNewFarm(@RequestBody FarmDto newFarmDto) {
    Farm newFarm = farmService.createFarm(newFarmDto.toFarm());
    return ResponseEntity.status(HttpStatus.CREATED).body((newFarm));
  }

  /**
   * Gets all farms.
   *
   * @return the all farms
   */
  @GetMapping
  public List<FarmDto> getAllFarms() {
    List<Farm> farms = farmService.getAllFarms();
    return farms.stream()
        .map((farm -> new FarmDto(
            farm.getId(),
            farm.getName(),
            farm.getSize()
        )))
        .toList();
  }

  /**
   * Gets farm by id.
   *
   * @param id the id
   * @return the farm by id
   */
  @GetMapping("/{id}")
  @Secured({"USER", "MANAGER", "ADMIN"})
  public ResponseEntity<Object> getFarmById(@PathVariable Integer id) {
    Optional<Farm> farm = farmService.getFarmById(id);

    if (farm.isPresent()) {
      return ResponseEntity.status(200).body(farm);
    } else {
      return ResponseEntity.status(404).body("Fazenda não encontrada!");
    }
  }

  /**
   * Create new crop response entity.
   *
   * @param farmId  the farm id
   * @param cropDto the crop dto
   * @return the response entity
   */
  @PostMapping("/{farmId}/crops")
  public ResponseEntity<?> createNewCrop(
      @PathVariable Integer farmId, @RequestBody CropDto cropDto
  ) {
    Farm farm = farmService.getFarmById(farmId)
        .orElseThrow(() -> new FarmNotFoundException("Fazenda não encontrada!"));

    Crop newCrop = cropService.createCrop(cropDto.toEntity(farm));

    farm.getCrops().add(newCrop);

    farmService.createFarm(farm);

    return ResponseEntity.status(HttpStatus.CREATED).body(CropDto.cropToDto(newCrop));
  }
}
