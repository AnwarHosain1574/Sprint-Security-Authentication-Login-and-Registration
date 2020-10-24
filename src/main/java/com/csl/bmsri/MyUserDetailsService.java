package com.csl.bmsri;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService{
	@Autowired
	private UserService userservice;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		User user = userservice.findUserByUserName(username);
		List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
		return buildUserForAuthentication(user, authorities);
	}
	
	private List<GrantedAuthority> getUserAuthority(Set<Role> userroles)
	{
		Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
		for(Role role : userroles) {
			roles.add(new SimpleGrantedAuthority(role.getRole()));
		}
		List<GrantedAuthority> granted = new ArrayList<>(roles);
		return granted;
	}
	
	private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities)
	{
		return new org.springframework.security.core.userdetails.User(user.getUsername()
				, user.getPassword(), user.getActive(), true, true, true, authorities);
	}
	
	
}
