package com.user.aspect;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.base.annotation.ValidateUserToken;
import com.base.security.Permissions;
import com.base.service.BaseSession;
import com.base.util.Log;
import com.user.entity.Employee;
import com.user.entity.RolePermission;
import com.user.exceptions.InvalidUserPermission;

/**
 * @author Muhil
 */
@Aspect
@Component
public class PermissionsAspect {

	@Autowired
	private BaseSession baseSession;

	@Pointcut("@within(com.base.annotation.ValidateUserToken) || @annotation(com.base.annotation.ValidateUserToken)")
	protected void userTokenPointCut() {

	}

	@Around(value = "userTokenPointCut ()")
	public Object enableTenantFilter(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		ValidateUserToken annotation = method.getAnnotation(ValidateUserToken.class);
		Permissions[] permissions = annotation.permissions();
		// validate for permissions only if present in annotation.
		if (permissions.length > 0) {
			Employee employee = (Employee) baseSession.getUserInfo();
			List<RolePermission> rolePermissions = CollectionUtils.emptyIfNull(employee.getEmployeeeRoles()).stream()
					.map(empRole -> empRole.getRole().getPermissions()).collect(Collectors.toList()).parallelStream()
					.flatMap(List::stream).collect(Collectors.toList());
			List<String> empPermissions = rolePermissions.parallelStream()
					.map(rolePermission -> rolePermission.getPermission().getPermission()).collect(Collectors.toList());
			if (permissions.length > 0 && empPermissions.isEmpty()) {
				Log.user.error("Employee doesnt seem to have required permission to access this endpoint");
				throw new InvalidUserPermission(
						"Employee doesnt seem to have required permission to access this endpoint");
			}
			if (!Stream.of(permissions).filter(prem -> empPermissions.contains(prem.getPermissionUniqueName()))
					.findFirst().isPresent()) {
				Log.user.error("Authorization denied for this to user to acces this endpoint");
				throw new InvalidUserPermission("Authorization denied for this to user to acces this endpoint");
			}
		}
		Log.user.debug(String.format("User Permissions are valid for method : %s required permissions %s", method.getName(),
				Permissions.getPermissionsAsString(permissions)));
		// TODO : we can do super user permission check to allow all endpoint
		Object resultObj = joinPoint.proceed();
		return resultObj;

	}

}
