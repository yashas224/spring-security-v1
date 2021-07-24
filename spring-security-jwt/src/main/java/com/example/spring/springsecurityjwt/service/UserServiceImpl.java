package com.example.spring.springsecurityjwt.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	private static Map<String, UserDetails> userMap = new HashMap();

	@PostConstruct
	private void setUsers() {
		List<GrantedAuthority> l = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"),
				new SimpleGrantedAuthority("ROLE_USER"));
		UserDetails user = new User("yashas", "yashasjwt", l);
		userMap.put("yashas", user);
		l = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
		user = new User("malathi", "malathijwt", l);
		userMap.put("malathi", user);

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails usr = userMap.get(username);
		if (usr == null) {
			return null;
		}
		// reconstructing User because the credentials are deleted internally by
		// sprig-security once authentication happens
		List<GrantedAuthority> list = new ArrayList<>();
		usr.getAuthorities().stream().forEach(au -> list.add(au));

		return new User(usr.getUsername(), usr.getPassword(), list);
	}

}
