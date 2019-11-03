package com.salil.driveme.datatransferobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salil.driveme.domainvalue.EngineType;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDTO
{
    private Long id;

    @NotNull(message = "License plate can not be empty")
    @Size(min = 7, max = 7)
    private String licensePlate;

    @Min(1)
    private int seatCount;

    private boolean convertible;

    @Min(1)
    @Max(5)
    private int rating;

    @NotNull(message = "Engine type cannot be empty")
    private EngineType engineType;

    @NotNull(message = "Manufacturer cannot be empty")
    private Long manufacturer;


    private CarDTO()
    {
    }


    public CarDTO(Long id, String licensePlate, int seatCount, boolean convertible, int rating, EngineType engineType, Long manufacturer)
    {
        this.id = id;
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
        this.convertible = convertible;
        this.rating = rating;
        this.engineType = engineType;
        this.manufacturer = manufacturer;
    }


    public Long getId()
    {
        return id;
    }


    public String getLicensePlate()
    {
        return licensePlate;
    }


    public int getSeatCount()
    {
        return seatCount;
    }


    public boolean isConvertible()
    {
        return convertible;
    }


    public int getRating()
    {
        return rating;
    }


    public EngineType getEngineType()
    {
        return engineType;
    }


    public Long getManufacturer()
    {
        return manufacturer;
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CarDTO carDTO = (CarDTO) o;
        return getSeatCount() == carDTO.getSeatCount() &&
            isConvertible() == carDTO.isConvertible() &&
            getRating() == carDTO.getRating() &&
            Objects.equals(getId(), carDTO.getId()) &&
            Objects.equals(getLicensePlate(), carDTO.getLicensePlate()) &&
            getEngineType() == carDTO.getEngineType() &&
            Objects.equals(getManufacturer(), carDTO.getManufacturer());
    }


    @Override
    public int hashCode()
    {
        return Objects.hash(getId(), getLicensePlate(), getSeatCount(), isConvertible(), getRating(), getEngineType(), getManufacturer());
    }


    public static class CarDTOBuilder
    {
        private Long id;
        private String licensePlate;
        private int seatCount;
        private boolean convertible;
        private int rating;
        private EngineType engineType;
        private Long manufacturer;


        public CarDTO.CarDTOBuilder setId(Long id)
        {
            this.id = id;
            return this;
        }


        public CarDTO.CarDTOBuilder setLicensePlate(String licensePlate)
        {
            this.licensePlate = licensePlate;
            return this;
        }


        public CarDTO.CarDTOBuilder setSeatCount(int seatCount)
        {
            this.seatCount = seatCount;
            return this;
        }


        public CarDTO.CarDTOBuilder setConvertible(boolean convertible)
        {
            this.convertible = convertible;
            return this;
        }


        public CarDTO.CarDTOBuilder setRating(int rating)
        {
            this.rating = rating;
            return this;
        }


        public CarDTO.CarDTOBuilder setEngineType(EngineType engineType)
        {
            this.engineType = engineType;
            return this;
        }


        public CarDTO.CarDTOBuilder setManufacturer(Long manufacturer)
        {
            this.manufacturer = manufacturer;
            return this;
        }


        public CarDTO createCarDTO()
        {
            return new CarDTO(id, licensePlate, seatCount, convertible, rating, engineType, manufacturer);
        }

    }
}
