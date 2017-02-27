package com.wilkestech.service.mapper;

import com.wilkestech.domain.*;
import com.wilkestech.service.dto.RenterDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Renter and its DTO RenterDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RenterMapper {

    RenterDTO renterToRenterDTO(Renter renter);

    List<RenterDTO> rentersToRenterDTOs(List<Renter> renters);

    @Mapping(target = "properties", ignore = true)
    Renter renterDTOToRenter(RenterDTO renterDTO);

    List<Renter> renterDTOsToRenters(List<RenterDTO> renterDTOs);
}
