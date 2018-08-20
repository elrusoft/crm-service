package com.polozhaev.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.polozhaev.business.entities.User;
import com.polozhaev.service.UserService;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	// return all users
	@PreAuthorize("hasRole('ROLE_ADMIN'")
	@RequestMapping(value = "/user")
	public ResponseEntity<List<User>> listAllUsers() {
		List<User> users = userService.findAllUsers();

		if (users.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	// return one user by id
	@RequestMapping(value = "/user/{username}")
	public ResponseEntity<User> getUser(@PathVariable String username) {

		User user = userService.getUser(username);
		if (user == null) {
			return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	// Method to create customer
	@RequestMapping(method = RequestMethod.POST, value = "/user/{idAdmin}")
	ResponseEntity<Void> createUser(@PathVariable int idAdmin, @RequestBody User user, UriComponentsBuilder ucBuilder) {

		if (userService.getUser(user.getUsername()) != null) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);

		}

		User admin = userService.getUserById(idAdmin);

		if (admin.isAdmin()) {
			userService.addUser(user);
		} else {
			return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());

		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}

	// Method to update user
	@RequestMapping(method = RequestMethod.PUT, value = "/user/{idAdmin}")
	ResponseEntity<User> updateUser(@PathVariable int idAdmin, @RequestBody User user) {

		User admin = userService.getUserById(idAdmin);

		if (admin.isAdmin()) {
			userService.updateUser(user);
		} else {
			return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);

	}

	// Method to delete user
	@RequestMapping(method = RequestMethod.DELETE, value = "{idAdmin}/user/{id}")
	ResponseEntity<Void> deleteUser(@PathVariable int idAdmin, @PathVariable int id, UriComponentsBuilder ucBuilder) {

		User admin = userService.getUserById(idAdmin);

		if (admin.isAdmin()) {

			userService.deleteUserById(id);
		} else {
			return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
		}

		HttpHeaders headers = new HttpHeaders();

		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}

	// Method to update user role admin
	@RequestMapping(method = RequestMethod.PUT, value = "/user/{idAdmin}/{iduser}")
	ResponseEntity<Void> updateUserRole(@PathVariable int idAdmin, @PathVariable int iduser) {

		User admin = userService.getUserById(idAdmin);
		if (admin.isAdmin()) {
			User user = userService.getUserById(iduser);
			user.setRole("ROLE_ADMIN");
			userService.updateUser(user);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}

		return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);

	}

	// this method is to create first user
	@RequestMapping("/user/save")
	public @ResponseBody String saveUser() {

		User user = new User();
		user.setEmail("crmservice@crm.com");
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		user.setPassword(passwordEncoder.encode("123456"));
		user.setUsername("test");
		user.setRole("ROLE_ADMIN");
		user.setEnabled(true);

		userService.addUser(user);

		return "done";
	}
}
