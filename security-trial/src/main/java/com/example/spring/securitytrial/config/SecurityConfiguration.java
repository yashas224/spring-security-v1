package com.example.spring.securitytrial.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		UserBuilder user = User.withUsername("yashas").password("samaga").roles("ADMIN");
		UserBuilder user1 = User.withUsername("malathi").password("licmalathi").roles("USER");

		auth.inMemoryAuthentication().withUser(user).withUser(user1);
	}

	@Bean
	public PasswordEncoder getENcode() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/admin").hasRole("ADMIN")
			.antMatchers("/user").hasAnyRole("USER", "ADMIN")
			.antMatchers("/**").permitAll()
			.and()
			.formLogin()
			.and()
			.logout();
	}

}
