package com.example.controller;

import com.example.DTO.requestDTO.CustomerAddressCreationDTO;
import com.example.DTO.requestDTO.CustomerCreationDTO;
import com.example.DTO.responseDTO.CustomerResponseDTO;
import com.example.model.Address;
import com.example.model.Customer;
import com.example.service.CustomerAddressService;
import com.example.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerAddressService customerAddressService;

    @GetMapping
    public List<CustomerResponseDTO> getAllCustomers() {
        return customerService.listAllCustomers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomer(@PathVariable String id) {
        return ResponseEntity.ok(customerService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(@Valid @RequestBody CustomerCreationDTO customerCreationDTO) {
        Customer newCustomer = customerCreationDTO.toEntity();
        Customer createdCustomer = customerService.createCustomer(newCustomer);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/customers/{id}")
                .buildAndExpand(createdCustomer.getId()).toUri();

        return ResponseEntity.created(location).body(createdCustomer.toResponseDTO());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/add/addresses/{customerId}")
    public ResponseEntity<CustomerResponseDTO> customerWithNewAddresses(@PathVariable String customerId, @Valid @RequestBody CustomerAddressCreationDTO customerAddressCreationDTO) {
        Address customerAddress = customerAddressService.createCustomerAddress(customerAddressCreationDTO);

        Customer customerWithNewAddresses = customerService.addAddressToCustomer(customerId, customerAddress);

        return ResponseEntity.ok(customerWithNewAddresses.toResponseDTO());
    }
}