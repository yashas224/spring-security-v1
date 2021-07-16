package com.example.spring.springsecurityjpa.repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spring.springsecurityjpa.entity.Client;

@Repository
public interface ClientRepositry extends JpaRepository<Client, Integer> {

	Client getByclientId(String username);

}
