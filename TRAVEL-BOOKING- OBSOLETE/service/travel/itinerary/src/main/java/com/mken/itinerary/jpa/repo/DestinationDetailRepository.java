package com.mken.itinerary.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mken.itinerary.entity.DestinationDetail;

/**
 * @author Muhil
 *
 */
@Repository
public interface DestinationDetailRepository extends JpaRepository<DestinationDetail, Long> {

}
