package com.example.controller;

import com.example.DTO.requestDTO.CustomerAddressCreationDTO;
import com.example.DTO.requestDTO.CustomerCreationDTO;
import com.example.DTO.requestDTO.CustomerUpdateDTO;
import com.example.DTO.responseDTO.CustomerResponseDTO;
import com.example.model.Address;
import com.example.model.Customer;
import com.example.service.CustomerAddressService;
import com.example.service.CustomerService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
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
    @CircuitBreaker(name = "customer")
    @TimeLimiter(name = "customer")
    @Retry(name = "customer")
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

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@PathVariable String id, @Valid @RequestBody CustomerUpdateDTO customerUpdateDTO) {
        Customer updatedCustomer = customerService.updateCustomer(id, customerUpdateDTO);

        return ResponseEntity.ok(updatedCustomer.toResponseDTO());
    }

    @PutMapping("/{id}/add/address")
    public ResponseEntity<CustomerResponseDTO> addAddressToCustomer(@PathVariable String id, @Valid @RequestBody CustomerAddressCreationDTO customerAddressCreationDTO) {
        Customer updatedCustomer = customerService.addAddressToCustomer(id, customerAddressCreationDTO);

        return ResponseEntity.ok(updatedCustomer.toResponseDTO());
    }

    @PutMapping("/{id}/remove/address/{addressId}")
    public ResponseEntity<CustomerResponseDTO> removeAddressFromCustomer(@PathVariable String id, @PathVariable String addressId) {
        Customer updatedCustomer = customerService.removeAddressFromCustomer(id, addressId);

        return ResponseEntity.ok(updatedCustomer.toResponseDTO());
    }

//    @PutMapping("/{id}/add/favourite-item/{productId}")
//    public ResponseEntity<CustomerResponseDTO> addToFavouriteList(@PathVariable String id, @PathVariable String productId) {
//
//    }
}
