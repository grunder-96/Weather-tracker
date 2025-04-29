package org.edu.pet.repository;

import org.edu.pet.model.Location;
import org.edu.pet.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {

    List<Location> findAllByUser(User user);

    Optional<Location> findByLongitudeAndLatitudeAndUser(BigDecimal longitude, BigDecimal latitude, User user);
}
