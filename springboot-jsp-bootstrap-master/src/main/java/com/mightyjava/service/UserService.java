package com.mightyjava.service;

import java.util.List;

import com.mightyjava.model.Role;
import com.mightyjava.model.Users;

public interface UserService {
	List<Users> userList();
	
	Users findOne(Long id);
	
	String addUser(Users user);
	
	String deleteUser(Long id);
	
	List<Role> roleList();
}
