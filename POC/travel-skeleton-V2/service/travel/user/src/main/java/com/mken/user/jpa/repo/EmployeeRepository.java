package com.mken.user.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mken.user.entity.Employee;

/**
 * @author Muhil
 *
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	final String findEmployeeByEmailIdQuery = "select emp from Employee emp where emp.emailid=:emailId";
	
	@Query(findEmployeeByEmailIdQuery)
	Employee findEmployeeByEmailId(@Param("emailId") String emailId);

}
