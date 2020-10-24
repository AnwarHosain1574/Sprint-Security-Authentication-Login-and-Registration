package com.csl.bmsri;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private BCryptPasswordEncoder bCryptpasswordencoder;
	
	@Autowired
	public UserService(
			UserRepository userRepository, 
			RoleRepository roleRepository,
			BCryptPasswordEncoder bCryptpasswordencoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.bCryptpasswordencoder = bCryptpasswordencoder;
	}
	
	public User findUserByEmail(String email)
	{
		return userRepository.findByEmail(email);
	}
	
	public User findUserByUserName(String username)
	{
		return userRepository.findByUsername(username);
	}
	
	public User saveuser(User user)
	{
		user.setPassword(bCryptpasswordencoder.encode(user.getPassword()));
		user.setActive(true);
		Role userrole = roleRepository.findByRole("ADMIN");
		user.setRoles(new HashSet<Role>(Arrays.asList(userrole)));
		return userRepository.save(user);
	}
	
	

}
