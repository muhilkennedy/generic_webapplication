package com.mken.itinerary.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mken.itinerary.entity.DestinationFileBlob;

/**
 * @author Muhil
 *
 */
@Repository
public interface DestinationFileBlobRepository extends JpaRepository<DestinationFileBlob, Long> {

}
