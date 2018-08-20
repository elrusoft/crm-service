package com.polozhaev.business.repositories;

import org.springframework.data.repository.CrudRepository;
import com.polozhaev.business.entities.Customer;;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

}
