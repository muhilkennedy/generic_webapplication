package com.user.service;

import java.util.List;

import org.apache.http.auth.AuthenticationException;

import com.base.service.BaseService;
import com.user.entity.User;
import com.user.exceptions.UserNotFoundException;

public interface UserService extends BaseService {
	
	public List findAll();

	public User register(User user);
	
	public void toggleUserStatus();
	
	public User login(User user) throws UserNotFoundException, AuthenticationException;
	
	public User findByEmailId(String emailId);
	
	public User findByEmailIdForTenant(String emailId, String tenantId);
	
	public void toggleUserStatus(String rootId);
	
}
