package com.salil.driveme.service.driver;

import com.salil.driveme.domainobject.DriverDO;
import com.salil.driveme.domainvalue.OnlineStatus;
import com.salil.driveme.exception.CarAlreadyInUseException;
import com.salil.driveme.exception.ConstraintsViolationException;
import com.salil.driveme.exception.EntityNotFoundException;
import com.salil.driveme.exception.IncorrectDriverStatusException;

import java.util.List;

public interface DriverService
{

    DriverDO find(Long driverId) throws EntityNotFoundException;

    DriverDO create(DriverDO driverDO) throws ConstraintsViolationException;

    void delete(Long driverId) throws EntityNotFoundException;

    void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException;

    List<DriverDO> find(OnlineStatus onlineStatus);

    DriverDO selectCar(final long driverId, final long carId) throws EntityNotFoundException, CarAlreadyInUseException, IncorrectDriverStatusException;

    DriverDO deSelectCar(final long driverId) throws EntityNotFoundException;

    List<DriverDO> search(final String search);

}
