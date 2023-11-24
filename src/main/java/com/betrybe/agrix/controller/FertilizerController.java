package com.betrybe.agrix.controller;

import com.betrybe.agrix.dto.FertilizerDto;
import com.betrybe.agrix.repository.Fertilizer;
import com.betrybe.agrix.service.FertilizerService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Fertilizer controller.
 */
@RestController
@RequestMapping("/fertilizers")
@PreAuthorize("hasAnyRole('ADMIN')")
public class FertilizerController {
  private final FertilizerService fertilizerService;

  @Autowired
  public FertilizerController(FertilizerService fertilizerService) {
    this.fertilizerService = fertilizerService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Fertilizer> createNewFertilizer(@RequestBody
      FertilizerDto newFertilizerDto) {
    Fertilizer newFertilizer = fertilizerService.createFertilizer(newFertilizerDto.toEntity());
    return ResponseEntity.status(HttpStatus.CREATED).body((newFertilizer));
  }

  /**
   * Gets all fertilizers.
   *
   * @return the all fertilizers
   */
  @GetMapping
  public List<FertilizerDto> getAllFertilizers() {
    List<Fertilizer> fertilizers = fertilizerService.getAllFertilizers();
    return fertilizers.stream()
        .map((fertilizer -> new FertilizerDto(
            fertilizer.getId(),
            fertilizer.getName(),
            fertilizer.getBrand(),
            fertilizer.getComposition()
        )))
        .toList();
  }

  /**
   * Gets fertilizer by id.
   *
   * @param id the id
   * @return the fertilizer by id
   */
  @GetMapping("/{id}")
  public ResponseEntity<Object> getFertilizerById(@PathVariable Integer id) {
    Optional<Fertilizer> fertilizer = fertilizerService.getFertilizerById(id);

    if (fertilizer.isPresent()) {
      return ResponseEntity.status(200).body(fertilizer);
    } else {
      return ResponseEntity.status(404).body("Fertilizante não encontrado!");
    }
  }
}
