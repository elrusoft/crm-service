package com.polozhaev.business.repositories;

import org.springframework.data.repository.CrudRepository;

import com.polozhaev.business.entities.User;

public interface UserRepository extends CrudRepository<User,Integer> {

	User findOneByUsername(String username);
	
	
}
