package com.salil.driveme.dataaccessobject;

import com.salil.driveme.domainobject.CarDO;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CarRepository extends CrudRepository<CarDO, Long>
{
    Optional<CarDO> findByLicensePlate(final String licensePlate);
}
