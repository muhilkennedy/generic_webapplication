package com.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.base.entity.SequenceNumber;

/**
 * @author Muhil Kennedy
 *
 */
@Repository
public interface SequenceNumberRepository extends JpaRepository<SequenceNumber, Long> {
	
	final String findNextSequenceByClassNameQuery = "select sn from SequenceNumber sn where sn.className = :className";
	
	@Query(findNextSequenceByClassNameQuery)
	SequenceNumber findSequenceByClassName(@Param("className") String className);

}
