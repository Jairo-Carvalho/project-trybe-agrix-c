package com.betrybe.agrix.service;

import com.betrybe.agrix.dto.CropDto;
import com.betrybe.agrix.dto.FertilizerDto;
import com.betrybe.agrix.repository.Crop;
import com.betrybe.agrix.repository.CropRepository;
import com.betrybe.agrix.repository.Farm;
import com.betrybe.agrix.repository.FarmRepository;
import com.betrybe.agrix.repository.Fertilizer;
import com.betrybe.agrix.repository.FertilizerRepository;
import com.betrybe.agrix.service.exception.CropNotFoundException;
import com.betrybe.agrix.service.exception.FarmNotFoundException;
import com.betrybe.agrix.service.exception.FertilizerNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Crop service.
 */
@Service
public class CropService {
  @Autowired
  private FarmRepository farmRepository;

  @Autowired
  private CropRepository cropRepository;

  @Autowired
  private FertilizerRepository fertilizerRepository;

  public Crop createCrop(Crop crop) {

    return cropRepository.save(crop);
  }

  /**
   * Gets all crops by farm id.
   *
   * @param farmId the farm id
   * @return the all crops by farm id
   */
  public List<CropDto> getAllCropsByFarmId(Integer farmId) {
    Optional<Farm> findFarm = farmRepository.findById(farmId);

    if (findFarm.isEmpty()) {
      throw new FarmNotFoundException("Fazenda não encontrada!");
    }

    List<Crop> crops = findFarm.get().getCrops();

    return crops.stream()
        .map(crop -> new CropDto(
            crop.getId(),
            crop.getName(),
            crop.getPlantedArea(),
            farmId,
            crop.getPlantedDate(),
            crop.getHarvestDate()
        )).collect(Collectors.toList());
  }

  public List<Crop> getAllCrops() {
    return cropRepository.findAll();
  }

  public Optional<Crop> getCropById(Integer id) {
    return cropRepository.findById(id);
  }

  /**
   * Gets crops by harvest date.
   *
   * @param start the start
   * @param end   the end
   * @return the crops by harvest date
   */
  public List<CropDto> getCropsByHarvestDate(LocalDate start, LocalDate end) {
    List<Crop> crops = cropRepository.findByHarvestDateBetween(start, end);

    return crops.stream()
        .map(crop -> new CropDto(
            crop.getId(),
            crop.getName(),
            crop.getPlantedArea(),
            crop.getFarm().getId(),
            crop.getPlantedDate(),
            crop.getHarvestDate()
        )).collect(Collectors.toList());
  }

  /**
   * Associate crop with fertilizer.
   *
   * @param cropId       the crop id
   * @param fertilizerId the fertilizer id
   */
  public void associateCropWithFertilizer(Integer cropId, Integer fertilizerId) {
    Crop crop = cropRepository.findById(cropId)
        .orElseThrow(() -> new CropNotFoundException("Plantação não encontrada!"));

    Fertilizer fertilizer = fertilizerRepository.findById(fertilizerId)
        .orElseThrow(() -> new FertilizerNotFoundException("Fertilizante não encontrado!"));

    crop.getFertilizer().add(fertilizer);
    cropRepository.save(crop);
  }

  /**
   * Gets fertilizers by crop id.
   *
   * @param cropId the crop id
   * @return the fertilizers by crop id
   */
  public List<FertilizerDto> getFertilizersByCropId(Integer cropId) {
    Optional<Crop> crop = cropRepository.findById(cropId);

    if (crop.isEmpty()) {
      throw new CropNotFoundException("Plantação não encontrada!");
    }

    List<Fertilizer> fertilizers = crop.get().getFertilizer();

    return fertilizers.stream()
        .map(fertilizer -> new FertilizerDto(
            fertilizer.getId(),
            fertilizer.getName(),
            fertilizer.getBrand(),
            fertilizer.getComposition()
        ))
        .toList();
  }
}
