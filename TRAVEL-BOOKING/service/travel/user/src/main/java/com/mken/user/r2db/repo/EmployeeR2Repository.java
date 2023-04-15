package com.mken.user.r2db.repo;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.mken.user.entity.Employee;

/**
 * @author Muhil
 *
 */
@Repository
public interface EmployeeR2Repository extends R2dbcRepository<Employee, Long>{

}
