package com.example.spring.springsecurityjdbc.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	
	
	
	// using default schema with H2 database
/*	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
		.dataSource(dataSource)
		.withDefaultSchema()
		.withUser(User.withUsername("yashas").password("yashas").roles("ADMIN"))
		.withUser( User.withUsername("tanvi").password("tanvi").roles("USER"));
	}*/

	
	// using schema.sql & dta.sql and H2 database
/*	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
		.dataSource(dataSource);
	}*/

	// using custome database with a different tabe aname and column names 
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
		.dataSource(dataSource)
		.usersByUsernameQuery("select client_id,password,is_active "
				+ "from MyTable "
				+ "where client_id = ?")
		.authoritiesByUsernameQuery("select client_id,role "
				+ "from permissions "
				+ "where client_id = ?");
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
		
		// to use H2 database
			http.csrf().disable();
			http.headers().frameOptions().disable();
	}

}
