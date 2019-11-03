package com.salil.driveme.service.driver;

import com.salil.driveme.controller.specification.CriteriaParser;
import com.salil.driveme.controller.specification.DriverSpecification;
import com.salil.driveme.controller.specification.GenericSpecificationsBuilder;
import com.salil.driveme.dataaccessobject.DriverRepository;
import com.salil.driveme.domainobject.CarDO;
import com.salil.driveme.domainobject.DriverDO;
import com.salil.driveme.domainvalue.GeoCoordinate;
import com.salil.driveme.domainvalue.OnlineStatus;
import com.salil.driveme.exception.CarAlreadyInUseException;
import com.salil.driveme.exception.ConstraintsViolationException;
import com.salil.driveme.exception.EntityNotFoundException;
import com.salil.driveme.exception.IncorrectDriverStatusException;
import com.salil.driveme.service.car.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some driver specific things.
 * <p/>
 */
@Service
public class DefaultDriverService implements DriverService
{

    private static final Logger LOG = LoggerFactory.getLogger(DefaultDriverService.class);

    private final DriverRepository driverRepository;

    private final CarService carService;


    public DefaultDriverService(final DriverRepository driverRepository, final CarService carService)
    {
        this.driverRepository = driverRepository;
        this.carService = carService;
    }


    /**
     * Selects a driver by id.
     *
     * @param driverId
     * @return found driver
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    public DriverDO find(Long driverId) throws EntityNotFoundException
    {
        return findDriverChecked(driverId);
    }


    /**
     * Creates a new driver.
     *
     * @param driverDO
     * @return
     * @throws ConstraintsViolationException if a driver already exists with the given username, ... .
     */
    @Override
    public DriverDO create(DriverDO driverDO) throws ConstraintsViolationException
    {
        DriverDO driver;
        try
        {
            driver = driverRepository.save(driverDO);
        }
        catch (DataIntegrityViolationException e)
        {
            LOG.warn("ConstraintsViolationException while creating a driver: {}", driverDO, e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return driver;
    }


    /**
     * Deletes an existing driver by id.
     *
     * @param driverId
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    @Transactional
    public void delete(Long driverId) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setDeleted(true);
    }


    /**
     * Update the location for a driver.
     *
     * @param driverId
     * @param longitude
     * @param latitude
     * @throws EntityNotFoundException
     */
    @Override
    @Transactional
    public void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setCoordinate(new GeoCoordinate(latitude, longitude));
    }


    /**
     * Find all drivers by online state.
     *
     * @param onlineStatus
     */
    @Override
    public List<DriverDO> find(OnlineStatus onlineStatus)
    {
        return driverRepository.findByOnlineStatus(onlineStatus);
    }


    private DriverDO findDriverChecked(final Long driverId) throws EntityNotFoundException
    {
        return driverRepository.findById(driverId)
            .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + driverId));
    }


    /**
     * Select car for driver
     * @param driverId
     * @param carId
     * @return
     * @throws EntityNotFoundException
     * @throws CarAlreadyInUseException
     * @throws IncorrectDriverStatusException
     */
    @Override
    @Transactional
    public DriverDO selectCar(final long driverId, final long carId) throws EntityNotFoundException, CarAlreadyInUseException, IncorrectDriverStatusException
    {
        DriverDO driver = findDriverChecked(driverId);

        if (driver.getOnlineStatus().equals(OnlineStatus.OFFLINE))
        {
            throw new IncorrectDriverStatusException("Driver with OFFLINE status cannot select a car");
        }

        CarDO car = carService.find(carId);
        if (car.isSelected())
        {
            throw new CarAlreadyInUseException("Car already in use by another driver");
        }

        car.setSelected(true);
        carService.save(car);

        Optional<CarDO> currentCar = Optional.ofNullable(driver.getCar());
        currentCar.ifPresent(c -> {
            c.setSelected(false);
            carService.save(c);
        });

        driver.setCar(car);
        driverRepository.save(driver);

        return driver;
    }


    @Override
    @Transactional
    public DriverDO deSelectCar(long driverId) throws EntityNotFoundException
    {
        DriverDO driver = findDriverChecked(driverId);

        Optional.ofNullable(driver.getCar()).ifPresent(c -> {
            c.setSelected(false);
            carService.save(c);
        });

        driver.setCar(null);
        driverRepository.save(driver);
        return driver;
    }


    /**
     * Search for drivers
     * @param search
     * @return
     */
    @Override
    public List<DriverDO> search(String search)
    {
        Specification<DriverDO> spec = resolveSpecificationFromInfixExpr(search);
        return driverRepository.findAll(spec);
    }


    private Specification<DriverDO> resolveSpecificationFromInfixExpr(String searchParameters)
    {
        GenericSpecificationsBuilder<DriverDO> specBuilder = new GenericSpecificationsBuilder<>();
        return specBuilder.build(new CriteriaParser().parse(searchParameters), DriverSpecification::new);
    }
}
