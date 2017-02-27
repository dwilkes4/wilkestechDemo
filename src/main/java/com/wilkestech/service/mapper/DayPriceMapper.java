package com.wilkestech.service.mapper;

import com.wilkestech.domain.*;
import com.wilkestech.service.dto.DayPriceDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity DayPrice and its DTO DayPriceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DayPriceMapper {

    @Mapping(source = "property.id", target = "propertyId")
    @Mapping(source = "property.id", target = "propertyId")
    DayPriceDTO dayPriceToDayPriceDTO(DayPrice dayPrice);

    List<DayPriceDTO> dayPricesToDayPriceDTOs(List<DayPrice> dayPrices);

    @Mapping(source = "propertyId", target = "property")
    @Mapping(source = "propertyId", target = "property")
    DayPrice dayPriceDTOToDayPrice(DayPriceDTO dayPriceDTO);

    List<DayPrice> dayPriceDTOsToDayPrices(List<DayPriceDTO> dayPriceDTOs);

    default Property propertyFromId(Long id) {
        if (id == null) {
            return null;
        }
        Property property = new Property();
        property.setId(id);
        return property;
    }
}
