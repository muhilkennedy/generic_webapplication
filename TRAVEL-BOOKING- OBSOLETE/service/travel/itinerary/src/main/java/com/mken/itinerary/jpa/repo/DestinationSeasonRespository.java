package com.mken.itinerary.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mken.itinerary.entity.DestinationSeason;

/**
 * @author Muhil
 *
 */
@Repository
public interface DestinationSeasonRespository extends JpaRepository<DestinationSeason, Long> {

	String getSeasonForMonthQuery = "select * from DestinationSeason where destinationid = :destinationId and month = :month and active = true";

	@Query(value = getSeasonForMonthQuery, nativeQuery = true)
	DestinationSeason findSeasonForMonth(@Param("destinationId") Long destinationId, @Param("month") int month);

}
