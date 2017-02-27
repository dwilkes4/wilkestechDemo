package com.wilkestech.service.mapper;

import com.wilkestech.domain.*;
import com.wilkestech.service.dto.DatesRentedDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity DatesRented and its DTO DatesRentedDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DatesRentedMapper {

    @Mapping(source = "rentee.id", target = "renteeId")
    @Mapping(source = "property.id", target = "propertyId")
    @Mapping(source = "property.id", target = "propertyId")
    @Mapping(source = "rentee.id", target = "renteeId")
    DatesRentedDTO datesRentedToDatesRentedDTO(DatesRented datesRented);

    List<DatesRentedDTO> datesRentedsToDatesRentedDTOs(List<DatesRented> datesRenteds);

    @Mapping(source = "renteeId", target = "rentee")
    @Mapping(source = "propertyId", target = "property")
    @Mapping(source = "propertyId", target = "property")
    @Mapping(source = "renteeId", target = "rentee")
    DatesRented datesRentedDTOToDatesRented(DatesRentedDTO datesRentedDTO);

    List<DatesRented> datesRentedDTOsToDatesRenteds(List<DatesRentedDTO> datesRentedDTOs);

    default Rentee renteeFromId(Long id) {
        if (id == null) {
            return null;
        }
        Rentee rentee = new Rentee();
        rentee.setId(id);
        return rentee;
    }

    default Property propertyFromId(Long id) {
        if (id == null) {
            return null;
        }
        Property property = new Property();
        property.setId(id);
        return property;
    }
}
