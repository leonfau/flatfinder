package de.leon.flatfinder.repositories;

import de.leon.flatfinder.entities.OfferEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends CrudRepository<OfferEntity, Long> {

    OfferEntity findByUrl(String url);
}
