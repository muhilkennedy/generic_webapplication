package com.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.base.entity.FileBlob;

/**
 * @author Muhil
 *
 */
@Repository
public interface FileBlobRepository extends JpaRepository<FileBlob, Long>{

}
