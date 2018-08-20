package com.polozhaev.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.polozhaev.business.entities.Customer;
import com.polozhaev.business.entities.User;
import com.polozhaev.service.CustomerService;
import com.polozhaev.service.UploadService;
import com.polozhaev.service.UserService;

@RestController

public class CustomerController {

	// Save the uploaded file to this folder
	private static String UPLOADED_FOLDER = "upload";

	@Autowired
	UserService userService;

	@Autowired
	CustomerService customerService;

	@Autowired
	UploadService UploadService;

	// return all customer
	@RequestMapping(value = "/customer")
	public ResponseEntity<List<Customer>> listAllCustomers() {
		List<Customer> customers = customerService.findAllCustomers();
		if (customers.isEmpty()) {
			return new ResponseEntity<List<Customer>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
	}

	// return one Customer by id
	@RequestMapping(value = "/customer/{id}")
	public ResponseEntity<Customer> getCustomer(@PathVariable String id) {

		Customer customer = customerService.getCustomerByIdCustomer(id);
		if (customer == null) {
			return new ResponseEntity<Customer>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}

	// Method to create customer
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

	// Method to update customer
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

	// Method to delete customer
	@RequestMapping(method = RequestMethod.DELETE, value = "/customer/{id}")
	ResponseEntity<Void> deletCustomer(@PathVariable int id, UriComponentsBuilder ucBuilder) {

		customerService.deleteCustomerById(id);

		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}

	// Method to upload image customer
	@RequestMapping(method = RequestMethod.POST, value = "/customer/{customerid}/upload")
	ResponseEntity<Customer> uploadPhotoCustomer(@PathVariable int customerid,
			@RequestParam("file") MultipartFile uploadfile) {

		if (uploadfile.isEmpty()) {
			return new ResponseEntity("please select a file!", HttpStatus.OK);
		}

		if (uploadfile.getContentType() != "image/png" || uploadfile.getContentType() != "image/jpeg") {

			return new ResponseEntity(HttpStatus.UNAUTHORIZED);

		}

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

	@RequestMapping("/customer/save")
	public String process() {

		Customer customer = new Customer();
		customer.setName("mikhail");
		customer.setSurname("polozhaev");
		customer.setIdCustomer("y2542484D");
		customerService.addCustomer(customer);

		return "done";
	}

}
