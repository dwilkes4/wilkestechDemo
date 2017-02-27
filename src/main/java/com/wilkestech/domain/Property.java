package com.wilkestech.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Property.
 */
@Entity
@Table(name = "property")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Property implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "property_name")
    private String propertyName;

    @ManyToOne
    private Renter renter;

    @OneToOne
    @JoinColumn(unique = true)
    private Address address;

    @OneToMany(mappedBy = "property")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DatesRented> datesRenteds = new HashSet<>();

    @OneToMany(mappedBy = "property")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DayPrice> dayPrices = new HashSet<>();

    @ManyToOne
    private Renter owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public Property propertyName(String propertyName) {
        this.propertyName = propertyName;
        return this;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Renter getRenter() {
        return renter;
    }

    public Property renter(Renter renter) {
        this.renter = renter;
        return this;
    }

    public void setRenter(Renter renter) {
        this.renter = renter;
    }

    public Address getAddress() {
        return address;
    }

    public Property address(Address address) {
        this.address = address;
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<DatesRented> getDatesRenteds() {
        return datesRenteds;
    }

    public Property datesRenteds(Set<DatesRented> datesRenteds) {
        this.datesRenteds = datesRenteds;
        return this;
    }

    public Property addDatesRented(DatesRented datesRented) {
        datesRenteds.add(datesRented);
        datesRented.setProperty(this);
        return this;
    }

    public Property removeDatesRented(DatesRented datesRented) {
        datesRenteds.remove(datesRented);
        datesRented.setProperty(null);
        return this;
    }

    public void setDatesRenteds(Set<DatesRented> datesRenteds) {
        this.datesRenteds = datesRenteds;
    }

    public Set<DayPrice> getDayPrices() {
        return dayPrices;
    }

    public Property dayPrices(Set<DayPrice> dayPrices) {
        this.dayPrices = dayPrices;
        return this;
    }

    public Property addDayPrice(DayPrice dayPrice) {
        dayPrices.add(dayPrice);
        dayPrice.setProperty(this);
        return this;
    }

    public Property removeDayPrice(DayPrice dayPrice) {
        dayPrices.remove(dayPrice);
        dayPrice.setProperty(null);
        return this;
    }

    public void setDayPrices(Set<DayPrice> dayPrices) {
        this.dayPrices = dayPrices;
    }

    public Renter getOwner() {
        return owner;
    }

    public Property owner(Renter renter) {
        this.owner = renter;
        return this;
    }

    public void setOwner(Renter renter) {
        this.owner = renter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Property property = (Property) o;
        if(property.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, property.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Property{" +
            "id=" + id +
            ", propertyName='" + propertyName + "'" +
            '}';
    }
}
