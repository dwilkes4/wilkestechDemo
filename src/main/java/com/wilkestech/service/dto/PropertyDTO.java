package com.wilkestech.service.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Property entity.
 */
public class PropertyDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8919350697847218368L;

	private Long id;

    private String propertyName;


    private Long renterId;
    
    private Long addressId;
    
    private Long ownerId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Long getRenterId() {
        return renterId;
    }

    public void setRenterId(Long renterId) {
        this.renterId = renterId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long renterId) {
        this.ownerId = renterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PropertyDTO propertyDTO = (PropertyDTO) o;

        if ( ! Objects.equals(id, propertyDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PropertyDTO{" +
            "id=" + id +
            ", propertyName='" + propertyName + "'" +
            '}';
    }
}
