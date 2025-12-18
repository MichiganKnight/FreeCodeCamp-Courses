package com.revature.weekTen.CustomerApp;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository {
    Customer save(Customer customer);
    Optional<Customer> findById(long id);
    void delete(Customer customer);
    List<Customer> findAll();
}
