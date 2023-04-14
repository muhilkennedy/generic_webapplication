package com.mken.user.service;

import org.apache.http.auth.AuthenticationException;

import com.mken.base.email.service.BaseService;
import com.mken.user.entity.User;
import com.platform.exception.UserNotFoundException;

public interface UserService extends BaseService {

	public User register(User user);
	
	public void toggleUserStatus(Long rootId);
	
	public User login(User user) throws AuthenticationException, UserNotFoundException;
	
	public User findByEmailId(String emailId);
	
}
