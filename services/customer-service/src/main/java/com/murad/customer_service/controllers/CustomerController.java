package com.murad.customer_service.controllers;

import com.murad.customer_service.dto.AddressRequest;
import com.murad.customer_service.dto.CustomerRequest;
import com.murad.customer_service.dto.CustomerResponse;
import com.murad.customer_service.dto.CustomerUpdateRequest;
import com.murad.customer_service.models.Address;
import com.murad.customer_service.services.AddressService;
import com.murad.customer_service.services.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody @Valid CustomerRequest request) {
        return ResponseEntity.ok(customerService.saveCustomer(request));
    }

    @GetMapping("/{id}")
    public CustomerResponse getCustomerById(@PathVariable Long id) {
        return customerService.findCustomer(id);
    }

    @GetMapping
    public ResponseEntity<CustomerResponse> searchCustomer(@RequestParam String email) {
        return ResponseEntity.ok(customerService.findCustomer(email));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id,
                                                           @RequestBody @Valid CustomerUpdateRequest request) {
        return ResponseEntity.ok(customerService.updateCustomer(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }

    /* Address methods */
    @PostMapping("/{customerId}/addresses")
    public ResponseEntity<String> addAddress(@PathVariable Long customerId, @RequestBody @Valid Set<Address> request) {
        return ResponseEntity.ok(addressService.saveAddresses(customerId, request));
    }

    @GetMapping("/{customerId}/addresses")
    public ResponseEntity<Set<Address>> getAddresses(@PathVariable Long customerId) {
        return ResponseEntity.ok().body( addressService.getAllAddressesByCustomer(customerId) );
    }

    @PutMapping("/{customerId}/addresses/{addressId}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long customerId, @PathVariable Long addressId, @RequestBody @Valid Address request) {
        return ResponseEntity.ok(addressService.updateAddress(customerId, addressId, request));
    }

    @DeleteMapping("/{customerId}/addresses/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long customerId, @PathVariable Long addressId) {
        addressService.deleteAddress(customerId, addressId);
        return ResponseEntity.ok().build();
    }
}
