package com.wilkestech.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


/**
 * A DTO for the DatesRented entity.
 */
public class DatesRentedDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1194911736125473919L;

	private Long id;

    private Long price;

    private LocalDate startDate;

    private LocalDate endDate;


    private Long renteeId;
    
    private Long propertyId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public Long getRenteeId() {
        return renteeId;
    }

    public void setRenteeId(Long renteeId) {
        this.renteeId = renteeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DatesRentedDTO datesRentedDTO = (DatesRentedDTO) o;

        if ( ! Objects.equals(id, datesRentedDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DatesRentedDTO{" +
            "id=" + id +
            ", price='" + price + "'" +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            '}';
    }
}
