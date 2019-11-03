package com.salil.driveme.controller;

import com.salil.driveme.controller.mapper.DriverMapper;
import com.salil.driveme.datatransferobject.DriverDTO;
import com.salil.driveme.domainobject.DriverDO;
import com.salil.driveme.domainvalue.OnlineStatus;
import com.salil.driveme.exception.CarAlreadyInUseException;
import com.salil.driveme.exception.ConstraintsViolationException;
import com.salil.driveme.exception.EntityNotFoundException;
import com.salil.driveme.exception.IncorrectDriverStatusException;
import com.salil.driveme.service.driver.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * All operations with a driver will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("v1/drivers")
public class DriverController
{

    private final DriverService driverService;


    @Autowired
    public DriverController(final DriverService driverService)
    {
        this.driverService = driverService;
    }


    @GetMapping("/{driverId}")
    public DriverDTO getDriver(@PathVariable long driverId) throws EntityNotFoundException
    {
        return DriverMapper.makeDriverDTO(driverService.find(driverId));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DriverDTO createDriver(@Valid @RequestBody DriverDTO driverDTO) throws ConstraintsViolationException
    {
        DriverDO driverDO = DriverMapper.makeDriverDO(driverDTO);
        return DriverMapper.makeDriverDTO(driverService.create(driverDO));
    }


    @DeleteMapping("/{driverId}")
    public void deleteDriver(@PathVariable long driverId) throws EntityNotFoundException
    {
        driverService.delete(driverId);
    }


    @PutMapping("/{driverId}")
    public void updateLocation(
        @PathVariable long driverId, @RequestParam double longitude, @RequestParam double latitude)
        throws EntityNotFoundException
    {
        driverService.updateLocation(driverId, longitude, latitude);
    }


    @GetMapping
    public List<DriverDTO> findDrivers(@RequestParam OnlineStatus onlineStatus)
    {
        return DriverMapper.makeDriverDTOList(driverService.find(onlineStatus));
    }


    @PostMapping("/{driverId}/car/{carId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void selectCar(@Valid @PathVariable long driverId, @PathVariable long carId)
        throws EntityNotFoundException, CarAlreadyInUseException, IncorrectDriverStatusException
    {
        driverService.selectCar(driverId, carId);
    }


    @DeleteMapping("/{driverId}/car") public void deSelectCar(@Valid @PathVariable long driverId) throws EntityNotFoundException
    {
        driverService.deSelectCar(driverId);
    }


    /**
     * Sample
     * http://localhost:8080/v1/drivers/spec?search=username:driver01 OR username:driver03
     */
    @GetMapping("/spec")
    @ResponseBody
    public List<DriverDO> search(@RequestParam(value = "search") String search)
    {
        return driverService.search(search);
    }
}
