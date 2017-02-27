package com.wilkestech.repository;

import com.wilkestech.domain.DatesRented;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DatesRented entity.
 */
@SuppressWarnings("unused")
public interface DatesRentedRepository extends JpaRepository<DatesRented,Long> {

}
