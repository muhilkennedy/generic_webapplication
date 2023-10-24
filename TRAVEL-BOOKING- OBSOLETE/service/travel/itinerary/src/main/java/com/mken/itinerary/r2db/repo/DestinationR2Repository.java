package com.mken.itinerary.r2db.repo;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.mken.itinerary.entity.Destination;

/**
 * @author Muhil
 *
 */
@Repository
public interface DestinationR2Repository extends R2dbcRepository<Destination, Long>{

}
