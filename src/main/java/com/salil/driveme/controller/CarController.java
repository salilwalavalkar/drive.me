package com.salil.driveme.controller;

import com.salil.driveme.controller.mapper.CarMapper;
import com.salil.driveme.datatransferobject.CarDTO;
import com.salil.driveme.datatransferobject.CarUpdateDTO;
import com.salil.driveme.domainobject.CarDO;
import com.salil.driveme.exception.ConstraintsViolationException;
import com.salil.driveme.exception.EntityNotFoundException;
import com.salil.driveme.service.car.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("v1/cars")
public class CarController
{
    private CarService carService;


    @Autowired
    public CarController(final CarService carService)
    {
        this.carService = carService;
    }


    @GetMapping
    public List<CarDTO> getCars()
    {
        return carService.find().stream()
            .map(CarMapper::makeCarDTO)
            .collect(Collectors.toList());
    }


    @GetMapping("/{carId}")
    public CarDTO getCar(@PathVariable long carId) throws EntityNotFoundException
    {
        return CarMapper.makeCarDTO(carService.find(carId));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarDTO createCar(@Valid @RequestBody CarDTO carDTO) throws EntityNotFoundException, ConstraintsViolationException
    {
        CarDO carDO =
            carService.create(carDTO.getLicensePlate(), carDTO.getManufacturer(), carDTO.getRating(), carDTO.getSeatCount(), carDTO.getEngineType(), carDTO.isConvertible());
        return CarMapper.makeCarDTO(carDO);
    }


    @DeleteMapping("/{carId}")
    public void deleteCar(@PathVariable long carId) throws EntityNotFoundException
    {
        carService.delete(carId);
    }


    @PutMapping("/{carId}")
    public CarDTO update(@PathVariable long carId, @Valid @RequestBody CarUpdateDTO carUpdateDTO) throws EntityNotFoundException
    {
        return CarMapper.makeCarDTO(carService.update(carId, carUpdateDTO.getRating()));
    }
}
