package com.mken.user.service;

import org.apache.http.auth.AuthenticationException;
import org.springframework.web.multipart.MultipartFile;

import com.mken.user.entity.User;
import com.platform.exception.UserNotFoundException;

import reactor.core.publisher.Flux;

/**
 * @author Muhil
 *
 */
public interface UserService {

	public User register(User user);
	
	public void toggleUserStatus(Long rootId);
	
	public User login(User user) throws AuthenticationException, UserNotFoundException;
	
	public User findByEmailId(String emailId);
	
	public User findById(Long rootId);
	
	public User updateProfilePic(MultipartFile file) throws Exception;
	
	public Flux<?> findAllReactive();
	
}
