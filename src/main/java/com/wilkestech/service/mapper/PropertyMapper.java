package com.wilkestech.service.mapper;

import com.wilkestech.domain.*;
import com.wilkestech.service.dto.PropertyDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Property and its DTO PropertyDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PropertyMapper {

    @Mapping(source = "renter.id", target = "renterId")
    @Mapping(source = "address.id", target = "addressId")
    @Mapping(source = "owner.id", target = "ownerId")
    PropertyDTO propertyToPropertyDTO(Property property);

    List<PropertyDTO> propertiesToPropertyDTOs(List<Property> properties);

    @Mapping(source = "renterId", target = "renter")
    @Mapping(source = "addressId", target = "address")
    @Mapping(target = "datesRenteds", ignore = true)
    @Mapping(target = "dayPrices", ignore = true)
    @Mapping(source = "ownerId", target = "owner")
    Property propertyDTOToProperty(PropertyDTO propertyDTO);

    List<Property> propertyDTOsToProperties(List<PropertyDTO> propertyDTOs);

    default Renter renterFromId(Long id) {
        if (id == null) {
            return null;
        }
        Renter renter = new Renter();
        renter.setId(id);
        return renter;
    }

    default Address addressFromId(Long id) {
        if (id == null) {
            return null;
        }
        Address address = new Address();
        address.setId(id);
        return address;
    }
}
