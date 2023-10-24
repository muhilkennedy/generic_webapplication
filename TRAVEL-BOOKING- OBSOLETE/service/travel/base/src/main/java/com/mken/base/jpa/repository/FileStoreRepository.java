package com.mken.base.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mken.base.entity.FileStore;

/**
 * @author Muhil
 *
 */
@Repository
public interface FileStoreRepository extends JpaRepository<FileStore, Long>  {

}
