package com.example.spring.springsecurityjpa.repositry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spring.springsecurityjpa.entity.Permission;

@Repository
public interface PermissionRepositry extends JpaRepository<Permission, Integer> {

	List<Permission> getByclientId(String username);

}
