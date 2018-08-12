package com.polozhaev.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.polozhaev.business.entities.User;
import com.polozhaev.business.repositories.UserRepository;

@Service
public class UserService {

	
	@Autowired
	private UserRepository userRepository;
	
	
	public List<User> findAllUsers(){
		
		
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(users::add);
		return users;
		
	}
	
	public void addUser(User user) {
		userRepository.save(user);
		
	}
	public User getUser(String username) {
		
		List<User> users = findAllUsers();
		
        for(User user : users){
            if(user.getUsername() == username){
                return user;
            }
        }
        return null;
	}
	public User getUserById(int id) {
		
		List<User> users = findAllUsers();
		
        for(User user : users){
            if(user.getId() == id){
                return user;
            }
        }
        return null;
	}
	
	public User findByUsername(String username) {
		List<User> users = findAllUsers();
		
        for(User user : users){
            if(user.getUsername().equalsIgnoreCase(username)){
                return user;
            }
        }
        return null;
    }
	public boolean isUserExist(User user) {
        return findByUsername(user.getUsername())!=null;
    }
	
	public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }
	
	public void updateUser(User user) {
		
		userRepository.save(user);
		
	}
	
}
