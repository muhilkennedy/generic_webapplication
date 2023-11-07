package com.mken.itinerary.jpa.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mken.itinerary.entity.Destination;

/**
 * @author Muhil
 *
 */
@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {
	
	String getAllParentDestinationQuery = "select dst from Destination dst where dst.parentid is null and dst.active=true";
	String getAllChildrenForDestinationQuery = "select dst from Destination dst where dst.parentid = :parentId and dst.active = true";
	String getAllParentDestinationIdQuery = "select rootid from Destination dst where dst.parentid is null and dst.active=true";
	String getAllChildrenForDestinationIdQuery = "select rootid from Destination dst where dst.parentid = :parentId and dst.active = true";
	
	@Query(getAllChildrenForDestinationQuery)
	List<Destination> findAllChildrenForDestination(@Param("parentId") Long parentRootId);
	
	@Query(getAllParentDestinationQuery)
	List<Destination> findAllParentDestination();
	
	@Query(getAllChildrenForDestinationIdQuery)
	List<Long> findAllChildrenIdsForDestination(@Param("parentId") Long parentRootId);
	
	@Query(getAllParentDestinationIdQuery)
	List<Long> findAllParentIdsDestination();

}
