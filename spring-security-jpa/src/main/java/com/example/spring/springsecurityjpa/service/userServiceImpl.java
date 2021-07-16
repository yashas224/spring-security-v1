package com.example.spring.springsecurityjpa.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.spring.springsecurityjpa.entity.Client;
import com.example.spring.springsecurityjpa.entity.Permission;
import com.example.spring.springsecurityjpa.repositry.ClientRepositry;
import com.example.spring.springsecurityjpa.repositry.PermissionRepositry;

@Service
public class userServiceImpl implements UserService {

	// the credentials , sensitive data that is password is set null after
	// authentication is done, to be on a safer side by the spring frameword
	/*
	 * if (this.eraseCredentialsAfterAuthentication && (result instanceof
	 * CredentialsContainer)) { // Authentication is complete. Remove credentials
	 * and other secret data // from authentication ((CredentialsContainer)
	 * result).eraseCredentials(); }
	 */

	////////////////////////////////////////////////////////////////////////
	
	
	// hard-coding to prove that UserdetailServce is not dependent on JPA
/*	private static Map<String, UserDetails> userMap = new HashMap();

	@PostConstruct
	private void setUsers() {
		List<GrantedAuthority> l = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"),
				new SimpleGrantedAuthority("ROLE_USER"));
		UserDetails user = new User("yashas", "test1Yashas", l);
		userMap.put("yashas", user);
		l = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
		user = new User("malathi", "test1Malathi", l);
		userMap.put("malathi", user);

	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails usr = userMap.get(username);
		if(usr == null) {
			return null;
		}
		// reconstructing User because the credentials are deleted internally by
		// sprig-security once authentication happens
		List<GrantedAuthority> list = new ArrayList<>();
		for (GrantedAuthority authority : usr.getAuthorities()) {
			list.add(authority);
		}

		return new User(usr.getUsername(), usr.getPassword(), list);
	}*/

	////////////////////////////////////////////////////////////////////////////

	@Autowired
	ClientRepositry clientRepositry;
	
	@Autowired
	PermissionRepositry permissionRepositry;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails user = null;
		Client client = clientRepositry.getByclientId(username);
		List<Permission> permission;
		if (client != null) {
			permission = permissionRepositry.getByclientId(username);
			if (permission != null) {
				List<GrantedAuthority> authorities = permission.stream()
						.map(p -> new SimpleGrantedAuthority(p.getRole())).collect(Collectors.toList());
				user = new User(username, client.getPassword(), authorities);
			}
		}
		return user;
	}
}
