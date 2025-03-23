package com.murad.order_service.clients.dto;

import java.util.Set;

public record CustomerResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        Set<AddressResponse> addresses
) {}
