package com.betrybe.agrix.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Crop repository.
 */
@Repository
public interface CropRepository extends JpaRepository<Crop, Integer> {

  List<Crop> findByFarmId(Integer farmId);

  List<Crop> findByHarvestDateBetween(LocalDate start, LocalDate end);
}
