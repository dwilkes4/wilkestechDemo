package com.wilkestech.repository;

import com.wilkestech.domain.Rentee;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Rentee entity.
 */
@SuppressWarnings("unused")
public interface RenteeRepository extends JpaRepository<Rentee,Long> {

}
