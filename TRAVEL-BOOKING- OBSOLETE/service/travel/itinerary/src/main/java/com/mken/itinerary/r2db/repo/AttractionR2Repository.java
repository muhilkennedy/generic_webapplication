package com.mken.itinerary.r2db.repo;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mken.itinerary.entity.Attraction;

import reactor.core.publisher.Flux;

/**
 * @author Muhil
 *
 */
@Repository
public interface AttractionR2Repository extends R2dbcRepository<Attraction, Long> {

	String getAllAttractionsForParentQuery = "select * from ATTRACTION where DESTINATIONID = :destinationId  and active = true";

	@Query(getAllAttractionsForParentQuery)
	Flux<Attraction> findAllAttractionsForParent(@Param("destinationId") Long destinationId);
}
