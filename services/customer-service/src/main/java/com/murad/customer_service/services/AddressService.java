package com.murad.customer_service.services;

import com.murad.customer_service.exceptions.AddressNotFoundException;
import com.murad.customer_service.exceptions.CustomerNotFoundException;
import com.murad.customer_service.models.Address;
import com.murad.customer_service.repositories.AddressRepository;
import com.murad.customer_service.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;

    /**
     * Saves multiple addresses for a given customer.
     * @param customerId the ID of the customer to whom the addresses belong
     * @param addresses a set of addresses to be saved
     * @return a string message indicating the addresses were saved successfully
     * @throws CustomerNotFoundException if no customer is found with the given ID
     */
    public String saveAddresses(Long customerId, Set<Address> addresses) {
        var customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId.toString()));
        var addressSet = customer.getAddresses();
        addresses.forEach(address -> address.setCustomer(customer));
        addressSet.addAll(addresses);
        addressRepository.saveAll(addressSet);
        return String.format("Addresses saved for customer with id %s", customerId);
    }

    /**
     * Gets all addresses associated with a given customer.
     * @param customerId the ID of the customer whose addresses are to be retrieved
     * @return a set of addresses linked to the specified customer
     * @throws CustomerNotFoundException if no customer is found with the given ID
     */
    public Set<Address> getAllAddressesByCustomer(Long customerId) {
        var customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId.toString()));
        return customer.getAddresses();
    }

    /**
     * Updates an address for a given customer.
     * @param customerId the ID of the customer to whom the address belongs
     * @param addressId the ID of the address to be updated
     * @param address the updated address details
     * @return the updated address
     * @throws CustomerNotFoundException if no customer is found with the given ID
     * @throws AddressNotFoundException if no address is found with the given ID
     */
    public Address updateAddress(Long customerId, Long addressId, Address address) {
        var customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId.toString()));
        var addressToUpdate = customer.getAddresses().stream()
                .filter(a -> a.getId().equals(addressId))
                .findFirst()
                .orElseThrow(AddressNotFoundException::new);
        addressToUpdate.setAddressLine1(address.getAddressLine1());
        addressToUpdate.setAddressLine2(address.getAddressLine2());
        addressToUpdate.setCity(address.getCity());
        addressToUpdate.setState(address.getState());
        addressToUpdate.setZipCode(address.getZipCode());

        return addressRepository.save(address);
    }

    /**
     * Deletes an address for a given customer.
     * @param customerId the ID of the customer to whom the address belongs
     * @param addressId the ID of the address to be deleted
     * @throws CustomerNotFoundException if no customer is found with the given ID
     * @throws AddressNotFoundException if no address is found with the given ID
     */
    public void deleteAddress(Long customerId, Long addressId) {
        var customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId.toString()));
        if (customer.getAddresses().stream().noneMatch(a -> a.getId().equals(addressId)))
            throw new AddressNotFoundException();
        addressRepository.deleteById(addressId);
    }
}
