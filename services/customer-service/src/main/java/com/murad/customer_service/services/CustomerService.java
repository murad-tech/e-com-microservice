package com.murad.customer_service.services;

import com.murad.customer_service.dto.CustomerRequest;
import com.murad.customer_service.dto.CustomerResponse;
import com.murad.customer_service.dto.CustomerUpdateRequest;
import com.murad.customer_service.exceptions.CustomerNotFoundException;
import com.murad.customer_service.models.Address;
import com.murad.customer_service.models.Customer;
import com.murad.customer_service.repositories.AddressRepository;
import com.murad.customer_service.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AddressService addressService;

    /**
     * Creates a new customer using the provided information.
     * @param customerRequest a request object containing the customer information
     * @return a string containing the ID of the newly created customer
     */
    public String saveCustomer(CustomerRequest customerRequest) {
        var customer = customerRepository.save(customerRequest.toCustomer());
        if (customerRequest.addresses() != null)
            addressService.saveAddresses(customer.getId(), customerRequest.addresses());
        return String.format("Customer created with id %s", customer.getId());
    }

    /**
     * Retrieves a customer by ID.
     * @param id the ID of the customer to find
     * @return a response object containing the customer details
     * @throws CustomerNotFoundException if no customer is found with the given ID
     */
    public CustomerResponse findCustomer(Long id) {
        var customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id.toString()));
        return new CustomerResponse(customer);
    }

    /**
     * Retrieves a customer by email address.
     * @param email the email address of the customer to find
     * @return a response object containing the customer details
     * @throws CustomerNotFoundException if no customer is found with the given email
     */
    public CustomerResponse findCustomer(String email) {
        var customer = customerRepository.findByEmail(email).orElseThrow(() -> new CustomerNotFoundException(email));
        return new CustomerResponse(customer);
    }

    /**
     * Updates an existing customer with the provided information.
     *
     * @param id      the ID of the customer to update
     * @param request the request object containing updated customer information
     * @return a response object containing the updated customer details
     * @throws CustomerNotFoundException if no customer is found with the given ID
     */
    public CustomerResponse updateCustomer(Long id, CustomerUpdateRequest request) {
        var customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id.toString()));

        mergeCustomer(customer, request);
        customerRepository.save(customer);

        return new CustomerResponse(customer);
    }

    /**
     * Deletes a customer by ID.
     * @param id the ID of the customer to delete
     */
    public void deleteCustomer(Long id) { customerRepository.deleteById(id); }

    /* private helper methods */
    private void mergeCustomer(Customer customer, CustomerUpdateRequest request) {
        customer.setFirstName(request.firstName() != null ? request.firstName() : customer.getFirstName());
        customer.setLastName(request.lastName() != null ? request.lastName() : customer.getLastName());
        customer.setEmail(request.email() != null ? request.email() : customer.getEmail());
        customer.setPhone(request.phone() != null ? request.phone() : customer.getPhone());
    }
}
