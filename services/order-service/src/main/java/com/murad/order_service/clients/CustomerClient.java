package com.murad.order_service.clients;

import com.murad.order_service.clients.dto.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "customer-service")
public interface CustomerClient {

    @GetMapping("/{customerId}")
    Optional<CustomerResponse> findCustomerById(@PathVariable Long customerId);
}
