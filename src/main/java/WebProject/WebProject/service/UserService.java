package WebProject.WebProject.service;

import java.util.List;

import WebProject.WebProject.entity.User;

public interface UserService {
	List<User> getAllUser();

	User saveUser(User user);

//	User getUserById(String loginname);

	User updateUser(User user);

	void deleteUserById(String id);
	
	User GetUserByEmail(String email);

	User findByIdAndRole(String id, String role);

	List<User> findAll();    
}
