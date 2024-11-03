package com.murad.customer_service.repositories;

import com.murad.customer_service.models.Address;
import com.murad.customer_service.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    /**
     * Finds a list of addresses associated with the given customer.
     *
     * @param customer the customer whose addresses are to be retrieved
     * @return a list of addresses linked to the specified customer
     */
    List<Address> findByCustomer(Customer customer);
}
