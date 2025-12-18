package com.revature.weekTen.CustomerApp;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(long id) {
        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("No Customer Found with ID: " + id));
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Customer customer) {
        Customer existingCustomer = getCustomerById(customer.getId());
        existingCustomer.setFirstName(customer.getFirstName());
        existingCustomer.setLastName(customer.getLastName());
        return customerRepository.save(existingCustomer);
    }

    public void deleteCustomer(long id) {
        Customer customerToDelete = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("No Customer Found with ID: " + id));
        customerRepository.delete(customerToDelete);
    }
}
