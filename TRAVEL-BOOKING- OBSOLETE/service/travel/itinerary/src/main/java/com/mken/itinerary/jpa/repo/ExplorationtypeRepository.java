package com.mken.itinerary.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mken.itinerary.entity.ExplorationType;

/**
 * @author Muhil
 *
 */
@Repository
public interface ExplorationtypeRepository extends JpaRepository<ExplorationType, Long> {

}
