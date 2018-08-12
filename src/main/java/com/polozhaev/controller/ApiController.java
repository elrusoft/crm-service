package com.polozhaev.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.polozhaev.business.entities.Customer;
import com.polozhaev.business.entities.User;
import com.polozhaev.business.repositories.CustomerRepository;
import com.polozhaev.business.repositories.UserRepository;
import com.polozhaev.service.CustomerService;
import com.polozhaev.service.UserService;
import com.polozhaev.service.UploadService;

@RestController
@RequestMapping("/api")
public class ApiController {

	// Save the uploaded file to this folder
	private static String UPLOADED_FOLDER = "upload";

	@Autowired
	CustomerService customerService;

	@Autowired
	UserService userService;

	@Autowired
	UploadService UploadService;

//agregar un actualizador de solo roles

//------------------------User api rest ---------------------------------//		
	// return all users
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

	@RequestMapping(method = RequestMethod.POST, value = "/user/{idAdmin}")
	ResponseEntity<Void> createUser(@PathVariable int idAdmin, @RequestBody User user, UriComponentsBuilder ucBuilder) {

		if (userService.getUser(user.getUsername()) != null) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);

		}

		User admin = userService.getUserById(idAdmin);

		if (admin.isAdmin()) {
			userService.addUser(user);
		}else {
			return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());

		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}

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

	@RequestMapping(method = RequestMethod.DELETE, value = "{idAdmin}/user/{id}")
	ResponseEntity<Void> deleteUser(@PathVariable int idAdmin, @PathVariable int id, UriComponentsBuilder ucBuilder) {

		User admin = userService.getUserById(idAdmin);

		if (admin.isAdmin()) {

			userService.deleteUserById(id);
		} else {
			return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
		}

		HttpHeaders headers = new HttpHeaders();
		// headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());

		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}

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

	// ------------------------ End User api rest
	// ---------------------------------//

	// en actualizar que solo actualizar el campo modifyby
	// en crear agregar la parte de usuarios tanto createby como modifyby
	// agregar lo de la foto

//------------------------Customer api rest ---------------------------------//		
	// return all users
	@RequestMapping(value = "/customer")
	public ResponseEntity<List<Customer>> listAllCustomers() {
		List<Customer> customers = customerService.findAllCustomers();
		if (customers.isEmpty()) {
			return new ResponseEntity<List<Customer>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
	}

	// return one user by id
	@RequestMapping(value = "/customer/{username}")
	public ResponseEntity<Customer> getCustomer(@PathVariable String username) {

		Customer customer = customerService.getCustomerByCreateUser(username);
		if (customer == null) {
			return new ResponseEntity<Customer>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/user/{userid}/customer")
	ResponseEntity<Void> createCustomer(@RequestBody Customer customer, UriComponentsBuilder ucBuilder,
			@PathVariable int userid) {

		User user = userService.getUserById(userid);

		if (customerService.getCustomerByIdCustomer(customer.getIdCustomer()) != null) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);

		}

		customer.setCreateby(user);
		customer.setModifyby(user);

		customerService.addCustomer(customer);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/customer/{id}").buildAndExpand(customer.getId()).toUri());

		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/user/{userid}/customer")
	ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer, @PathVariable int userid) {

		Customer cust = customerService.getCustomerByIdCustomer(customer.getIdCustomer());

		if (cust == null) {

			return new ResponseEntity<Customer>(HttpStatus.NO_CONTENT);
		}

		User user = userService.getUserById(userid);

		customer.setId(cust.getId());
		customer.setModifyby(user);

		customerService.updateCustomer(customer);

		return new ResponseEntity<Customer>(customer, HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/customer/{id}")
	ResponseEntity<Void> deletCustomer(@PathVariable int id, UriComponentsBuilder ucBuilder) {

		customerService.deleteCustomerById(id);

		HttpHeaders headers = new HttpHeaders();
		// headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());

		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/customer/{customerid}/upload")
	ResponseEntity<Customer> uploadPhotoCustomer(@PathVariable int customerid,
			@RequestParam("file") MultipartFile uploadfile) {

		if (uploadfile.isEmpty()) {
			return new ResponseEntity("please select a file!", HttpStatus.OK);
		}

		// image/png , image/jpeg
		System.out.println("type: " + uploadfile.getContentType());

		Customer customer = customerService.findById(customerid);

		if (customer == null) {

			return new ResponseEntity<Customer>(HttpStatus.NO_CONTENT);
		}

		try {

			UploadService.saveUploadedFile(uploadfile, customer.getIdCustomer());

		} catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		customer.setPhoto(UPLOADED_FOLDER + "/" + customer.getIdCustomer() + "/" + uploadfile.getOriginalFilename());

		customerService.updateCustomer(customer);

		return new ResponseEntity<Customer>(customer, HttpStatus.OK);

	}

	// ------------------------ End User api rest
	// ---------------------------------//

	@RequestMapping("customer/save")
	public String process() {

		Customer customer = new Customer();
		customer.setName("mikhail");
		customer.setSurname("polozhaev");
		customer.setIdCustomer("y2542484D");
		// customer.setCreateby(1);
		// customer.setModifyby(1);
		customerService.addCustomer(customer);

		return "done";
	}

	@RequestMapping("user/save")
	public @ResponseBody String saveUser() {

		User user = new User();
		user.setEmail("mikhail.polozhaev@gmail.com");
		user.setPassword("1234");
		user.setUsername("elrusoft");
		user.setRole("admin");

		userService.addUser(user);

		return "done";
	}

}
