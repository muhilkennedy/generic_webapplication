package com.mken.user.serviceimpl;

import java.io.File;
import java.nio.file.Files;

import org.apache.commons.io.FileUtils;
import org.apache.http.auth.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mken.base.session.BaseSession;
import com.mken.base.util.Log;
import com.mken.base.util.SecurityUtil;
import com.mken.user.emailservice.EmployeeEmailService;
import com.mken.user.entity.Employee;
import com.mken.user.entity.User;
import com.mken.user.service.EmployeeService;
import com.platform.exception.UserNotFoundException;
import com.platform.service.StorageService;
import com.platform.util.FileUtil;
import com.platform.util.ImageUtil;

import reactor.core.publisher.Flux;

/**
 * @author Muhil
 *
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeEmailService emailService;
	
	@Autowired
	private EmployeeDaoService employeeDao;
	
	@Override
	public User findByEmailId(String emailId) {
		return employeeDao.findByEmailId(emailId);
	}

	@Override
	public Flux<?> findAllReactive() {
		return employeeDao.findAllReactive();
	}

	@Override
	public User findById(Long rootId) {
		return (User) employeeDao.findById(rootId);
	}

	@Override
	public void toggleUserStatus(Long rootId) {
		Employee emp = (Employee) employeeDao.findById(rootId);
		if(emp != null) {
			emp.setActive(!emp.isActive());
			employeeDao.save(emp);
		}
		else {
			//throw exception
		}
	}
	
	@Override
	public User register(User user) {
		Employee employee = (Employee) user;
		String encrptedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(SecurityUtil.PASSWORD_SALT_ROUNDS));
		employee.setPassword(encrptedPassword);
		employee = (Employee) employeeDao.saveAndFlush(employee);
		return employee;
	}

	@Override
	public User login(User user) throws AuthenticationException, UserNotFoundException {
		Employee employee = (Employee) employeeDao.findByEmailId(user.getEmailId());
		if(employee == null) {
			throw new UserNotFoundException();
		}
		if(!employee.isActive()) {
			throw new AuthenticationException("Employee Account is deactivated!");
		}
		if(!BCrypt.checkpw(user.getPassword(), employee.getPassword())) {
			throw new AuthenticationException("Invalid Password");
		}
		return employee;
	}
	
	@Override
	public Employee createAdminEmployee(Employee employee) {
		String generatedPassword = SecurityUtil.generateRandomPassword();
		Log.user.debug(String.format("Generated password for user {%s} is {%s}", employee.getfName(), generatedPassword));
		String encrptedPassword = BCrypt.hashpw(generatedPassword, BCrypt.gensalt(SecurityUtil.PASSWORD_SALT_ROUNDS));
		employee.setPassword(encrptedPassword);
		employee.setDesignation("CustomerSupportAdmin");
		employee = (Employee) employeeDao.saveAndFlush(employee);
		// send email
		emailService.sendWelcomeActivationEmail(employee, generatedPassword);
		return employee;
	}
	
	@Override
	public User updateProfilePic(MultipartFile file) throws Exception {
		User user = (User) BaseSession.getUser();
		File ff;
		ff = new File(FileUtil.getTempDirectory() + user.getRootId()
				+ FileUtil.getFileExtension(file.getOriginalFilename()));
		ff.mkdirs();
		file.transferTo(ff);
		byte[] image = ImageUtil.getThumbnailImage(Files.readAllBytes(ff.toPath()));
		FileUtils.writeByteArrayToFile(ff, image);
		String piciurl = StorageService.getStorage().saveFile(ff, "profilepics/employee");
		user.setProfilePic(piciurl);
		employeeDao.save(user);
		return user;
	}

}
