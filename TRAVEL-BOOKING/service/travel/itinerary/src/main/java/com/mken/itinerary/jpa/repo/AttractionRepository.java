package com.mken.itinerary.jpa.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mken.itinerary.entity.Attraction;

/**
 * @author muhil
 *
 */
@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Long> {
	
	String getAllAttractionsForParentQuery = "select atr from Attraction atr where atr.destinationid = :destinationId and atr.active = true";

	@Query(getAllAttractionsForParentQuery)
	List<Attraction> findAllAttractionsForParent(@Param("destinationId") Long destinationId);
	
}
