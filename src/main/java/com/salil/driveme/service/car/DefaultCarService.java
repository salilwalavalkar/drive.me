package com.salil.driveme.service.car;

import com.salil.driveme.dataaccessobject.CarRepository;
import com.salil.driveme.dataaccessobject.ManufacturerRepository;
import com.salil.driveme.domainobject.CarDO;
import com.salil.driveme.domainobject.ManufacturerDO;
import com.salil.driveme.domainvalue.EngineType;
import com.salil.driveme.exception.ConstraintsViolationException;
import com.salil.driveme.exception.EntityNotFoundException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultCarService implements CarService
{

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(DefaultCarService.class);

    private CarRepository carRepository;
    private ManufacturerRepository manufacturerRepository;


    @Autowired
    public DefaultCarService(CarRepository carRepository, ManufacturerRepository manufacturerRepository)
    {
        this.carRepository = carRepository;
        this.manufacturerRepository = manufacturerRepository;
    }


    /**
     * Create a car entity
     * @param licensePlate
     * @param manufacturerId
     * @param rating
     * @param seatCount
     * @param engineType
     * @param convertible
     * @return
     * @throws EntityNotFoundException
     * @throws ConstraintsViolationException
     */
    @Override
    @Transactional
    public CarDO create(final String licensePlate, final Long manufacturerId, final int rating, final int seatCount, final EngineType engineType, final boolean convertible)
        throws EntityNotFoundException, ConstraintsViolationException
    {
        Optional<ManufacturerDO> manufacturer = manufacturerRepository.findById(manufacturerId);

        manufacturer.orElseThrow(() -> {
            return new EntityNotFoundException("A manufacturer with this id does not exist: " + manufacturerId);
        });

        if (carRepository.findByLicensePlate(licensePlate).isPresent())
        {
            throw new ConstraintsViolationException("A car with this license plate already exists: " + licensePlate);
        }

        CarDO car = new CarDO(licensePlate, manufacturer.get(), engineType, rating, convertible, seatCount);
        try
        {
            return carRepository.save(car);
        }
        catch (DataIntegrityViolationException e)
        {
            throw new ConstraintsViolationException(e.getMessage());
        }
    }


    /**
     * Find all cars
     * @return
     */
    @Override
    public List<CarDO> find()
    {
        List<CarDO> allCars = new ArrayList<>();
        carRepository.findAll().forEach(allCars::add);
        return allCars;
    }


    /**
     * Find car by id
     * @param carId
     * @return
     * @throws EntityNotFoundException
     */
    @Override
    public CarDO find(final long carId) throws EntityNotFoundException
    {
        Optional<CarDO> car = carRepository.findById(carId);
        return car.orElseThrow(() -> {
            return new EntityNotFoundException("A car with this id does not exist: " + carId);
        });
    }


    /**
     * Save car details
     * @param carDO
     * @return
     */
    @Override
    @Transactional
    public CarDO save(final CarDO carDO)
    {
        return carRepository.save(carDO);
    }


    /**
     * Delete car by id
     * @param carId
     */
    @Override
    @Transactional
    public void delete(long carId)
    {
        carRepository.deleteById(carId);
    }


    /**
     * Update car rating by car id
     * @param carId
     * @param rating
     * @return
     * @throws EntityNotFoundException
     */
    @Override
    @Transactional
    public CarDO update(final long carId, final int rating) throws EntityNotFoundException
    {
        Optional<CarDO> carDO = carRepository.findById(carId);

        CarDO carDOToUpdate = carDO.orElseThrow(() -> {
            return new EntityNotFoundException("Could not find car with id: " + carId);
        });

        carDOToUpdate.setRating(rating);

        return save(carDOToUpdate);
    }
}
