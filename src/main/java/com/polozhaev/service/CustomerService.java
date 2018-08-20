package com.polozhaev.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.polozhaev.business.entities.Customer;
import com.polozhaev.business.entities.User;
import com.polozhaev.business.repositories.CustomerRepository;
import com.polozhaev.business.repositories.UserRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	public List<Customer> findAllCustomers() {

		List<Customer> customers = new ArrayList<>();
		customerRepository.findAll().forEach(customers::add);
		return customers;

	}

	public void addCustomer(Customer customer) {
		customerRepository.save(customer);

	}

	public Customer getCustomerByCreateUser(String username) {

		List<Customer> customers = findAllCustomers();

		for (Customer customer : customers) {
			if (customer.getCreateby().getUsername().equalsIgnoreCase(username)) {
				return customer;
			}
		}
		return null;
	}

	public Customer getCustomerByUpdateUser(String username) {

		List<Customer> customers = findAllCustomers();

		for (Customer customer : customers) {
			if (customer.getModifyby().getUsername().equalsIgnoreCase(username)) {
				return customer;
			}
		}
		return null;
	}

	public Customer getCustomerByIdCustomer(String id) {

		List<Customer> customers = findAllCustomers();

		for (Customer customer : customers) {

			if (customer.getIdCustomer().equals(id)) {
				return customer;
			}
		}

		return null;
	}

	public Customer findById(int id) {

		List<Customer> customers = findAllCustomers();

		for (Customer customer : customers) {
			if (customer.getId() == id) {
				return customer;
			}
		}
		return null;
	}

	public boolean isUserExist(Customer customer) {
		return getCustomerByIdCustomer(customer.getIdCustomer()) != null;
	}

	public void deleteCustomerById(int id) {
		customerRepository.deleteById(id);
	}

	public void updateCustomer(Customer customer) {

		customerRepository.save(customer);

	}

}
