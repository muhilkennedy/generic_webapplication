package com.base.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.base.entity.FileStore;

/**
 * @author Muhil
 *
 */
@Repository
public interface FileStoreRepository extends JpaRepository<FileStore, Long>  {

}
