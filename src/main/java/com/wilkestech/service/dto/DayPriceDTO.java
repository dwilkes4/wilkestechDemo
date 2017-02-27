package com.wilkestech.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


/**
 * A DTO for the DayPrice entity.
 */
public class DayPriceDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -756444961430821841L;

	private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    private Long priceOfDay;


    private Long propertyId;
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public Long getPriceOfDay() {
        return priceOfDay;
    }

    public void setPriceOfDay(Long priceOfDay) {
        this.priceOfDay = priceOfDay;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DayPriceDTO dayPriceDTO = (DayPriceDTO) o;

        if ( ! Objects.equals(id, dayPriceDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DayPriceDTO{" +
            "id=" + id +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            ", priceOfDay='" + priceOfDay + "'" +
            '}';
    }
}
