package com.wilkestech.repository;

import com.wilkestech.domain.Renter;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Renter entity.
 */
@SuppressWarnings("unused")
public interface RenterRepository extends JpaRepository<Renter,Long> {

}
