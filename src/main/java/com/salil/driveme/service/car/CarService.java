package com.salil.driveme.service.car;

import com.salil.driveme.domainobject.CarDO;
import com.salil.driveme.domainvalue.EngineType;
import com.salil.driveme.exception.ConstraintsViolationException;
import com.salil.driveme.exception.EntityNotFoundException;

import java.util.List;

public interface CarService
{
    List<CarDO> find();

    CarDO find(final long carId) throws EntityNotFoundException;

    CarDO create(final String licensePlate, final Long manufacturerId, final int rating, final int seatCount, final EngineType engineType, final boolean convertible)
        throws EntityNotFoundException, ConstraintsViolationException;

    void delete(final long carId);

    CarDO save(final CarDO carDO);

    CarDO update(final long carId, final int rating) throws EntityNotFoundException;
}
