package com.salil.driveme.domainobject;

import com.salil.driveme.domainvalue.EngineType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(
    name = "car",
    uniqueConstraints = @UniqueConstraint(name = "uc_license_plate", columnNames = {"licensePlate"})
)
public class CarDO
{
    @Id
    @SequenceGenerator(name = "car_seq", sequenceName = "car_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "car_seq")
    private Long id;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCreated;

    @Column(nullable = false)
    @NotNull(message = "License plate can not be null!")
    private String licensePlate;

    private int seatCount;

    private boolean convertible;

    private int rating;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Engine type can not be null!")
    private EngineType engineType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "manufacturer_id", nullable = false, updatable = false)
    private ManufacturerDO manufacturer;

    private boolean selected;


    public CarDO(String licensePlate, ManufacturerDO manufacturer, EngineType engineType, int rating, boolean convertible, int seatCount)
    {
        this.id = null;
        this.manufacturer = manufacturer;
        this.engineType = engineType;
        this.rating = rating;
        this.convertible = convertible;
        this.seatCount = seatCount;
        this.licensePlate = licensePlate;
        this.dateCreated = ZonedDateTime.now();
        this.selected = false;
    }


    private CarDO()
    {
    }


    public Long getId()
    {
        return id;
    }


    public ZonedDateTime getDateCreated()
    {
        return ZonedDateTime.from(dateCreated);
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


    public void setRating(int rating)
    {
        this.rating = rating;
    }


    public EngineType getEngineType()
    {
        return engineType;
    }


    public ManufacturerDO getManufacturer()
    {
        return manufacturer;
    }


    public boolean isSelected()
    {
        return selected;
    }


    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CarDO carDo = (CarDO) o;
        return getSeatCount() == carDo.getSeatCount() &&
            isConvertible() == carDo.isConvertible() &&
            getRating() == carDo.getRating() &&
            selected == carDo.selected &&
            Objects.equals(getId(), carDo.getId()) &&
            Objects.equals(getDateCreated(), carDo.getDateCreated()) &&
            Objects.equals(getLicensePlate(), carDo.getLicensePlate()) &&
            getEngineType() == carDo.getEngineType() &&
            Objects.equals(getManufacturer(), carDo.getManufacturer());
    }


    @Override
    public int hashCode()
    {
        return Objects.hash(getId(), getDateCreated(), getLicensePlate(), getSeatCount(), isConvertible(), getRating(), getEngineType(), getManufacturer(), selected);
    }
}
