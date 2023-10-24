package com.mken.itinerary.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mken.itinerary.entity.AttractionSeason;

/**
 * @author Muhil
 *
 */
@Repository
public interface AttractionSeasonRepository extends JpaRepository<AttractionSeason, Long> {

}
