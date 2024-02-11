package com.example.service;

import com.example.DTO.responseDTO.CustomerResponseDTO;
import com.example.exception.NotFoundException;
import com.example.model.Customer;
import com.example.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<CustomerResponseDTO> listAllCustomers() {
        return customerRepository.findAll().stream().map(Customer::toResponseDTO).collect(Collectors.toList());
    }

    public CustomerResponseDTO findById(String id) {
        Customer customer = customerRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Product not found with id " + id));

        return customer.toResponseDTO();
    }


}
