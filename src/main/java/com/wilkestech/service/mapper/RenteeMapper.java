package com.wilkestech.service.mapper;

import com.wilkestech.domain.*;
import com.wilkestech.service.dto.RenteeDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Rentee and its DTO RenteeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RenteeMapper {

    RenteeDTO renteeToRenteeDTO(Rentee rentee);

    List<RenteeDTO> renteesToRenteeDTOs(List<Rentee> rentees);

    @Mapping(target = "datesRenteds", ignore = true)
    Rentee renteeDTOToRentee(RenteeDTO renteeDTO);

    List<Rentee> renteeDTOsToRentees(List<RenteeDTO> renteeDTOs);
}
