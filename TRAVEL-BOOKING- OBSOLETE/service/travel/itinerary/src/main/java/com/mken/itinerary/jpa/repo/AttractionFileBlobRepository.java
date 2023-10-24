package com.mken.itinerary.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mken.itinerary.entity.AttractionFileBlob;

/**
 * @author Muhil
 *
 */
@Repository
public interface AttractionFileBlobRepository extends JpaRepository<AttractionFileBlob, Long> {

}
