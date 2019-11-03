package com.salil.driveme.controller.mapper;

import com.salil.driveme.datatransferobject.CarDTO;
import com.salil.driveme.domainobject.CarDO;
import com.salil.driveme.domainobject.ManufacturerDO;

import java.util.Optional;

public class CarMapper
{
    public static CarDTO makeCarDTO(CarDO car)
    {
        return Optional.ofNullable(car).map(c -> {
            Long manufacturerId = Optional.ofNullable(car.getManufacturer())
                .map(ManufacturerDO::getId).orElse(null);

            return new CarDTO(
                c.getId(),
                c.getLicensePlate(),
                c.getSeatCount(),
                c.isConvertible(),
                c.getRating(),
                c.getEngineType(),
                manufacturerId);
        }).orElse(null);
    }
}
