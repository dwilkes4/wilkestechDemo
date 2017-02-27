package com.wilkestech.repository;

import com.wilkestech.domain.DayPrice;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DayPrice entity.
 */
@SuppressWarnings("unused")
public interface DayPriceRepository extends JpaRepository<DayPrice,Long> {

}
