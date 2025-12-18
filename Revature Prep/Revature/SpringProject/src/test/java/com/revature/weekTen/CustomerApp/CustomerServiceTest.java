package com.revature.weekTen.CustomerApp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void testCreateCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");

        when(customerRepository.save(customer)).thenReturn(customer);

        Customer savedCustomer = customerService.createCustomer(customer);

        Assertions.assertEquals(customer, savedCustomer);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    public void testGetCustomerById() {
        long customerId = 1L;
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setFirstName("John");
        customer.setLastName("Doe");

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        Customer foundCustomer = customerService.getCustomerById(customerId);

        Assertions.assertEquals(customer, foundCustomer);
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    public void testDeleteCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("John");
        customer.setLastName("Doe");

        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

        customerService.deleteCustomer(customer.getId());

        verify(customerRepository, times(1)).findById(customer.getId());
        verify(customerRepository, times(1)).delete(customer);
    }
}
