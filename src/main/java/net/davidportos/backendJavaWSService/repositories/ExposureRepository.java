package net.davidportos.backendJavaWSService.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.davidportos.backendJavaWSService.entities.ExposureEntity;


@Repository
public interface ExposureRepository extends CrudRepository<ExposureEntity, Long> {
    ExposureEntity findById(long id);

    
}
