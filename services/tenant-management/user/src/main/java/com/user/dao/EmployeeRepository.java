package com.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.user.entity.Employee;

/**
 * @author Muhil
 *
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
	
	final String findEmployeeByEmailIdQuery = "select emp from Employee emp where emp.emailId=:emailId";
	
	@Query(findEmployeeByEmailIdQuery)
	Employee findEmployeeByEmailId(@Param("emailId") String emailId);
	
	final String findEmployeeByEmailIdForTenantQuery = "select emp from Employee emp where emp.tenantId=:tenantId and emp.emailId=:emailId";
	
	@Query(findEmployeeByEmailIdForTenantQuery)
	Employee findEmployeeByEmailIdByTenant(@Param("tenantId") String tenantId, @Param("emailId") String emailId);


}
