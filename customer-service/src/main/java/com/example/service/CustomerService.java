package com.example.service;

import com.example.DTO.requestDTO.CustomerAddressCreationDTO;
import com.example.DTO.requestDTO.CustomerCreationDTO;
import com.example.DTO.responseDTO.CustomerResponseDTO;
import com.example.exception.NotFoundException;
import com.example.model.Address;
import com.example.model.Customer;
import com.example.repository.CustomerAddressRepository;
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
    private final CustomerAddressRepository customerAddressRepository;

    public List<CustomerResponseDTO> listAllCustomers() {
        return customerRepository.findAll().stream().map(Customer::toResponseDTO).collect(Collectors.toList());
    }

    public CustomerResponseDTO findById(String id) {
        Customer customer = customerRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Product not found with id " + id));

        return customer.toResponseDTO();
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }

    public Customer addAddressToCustomer(String customerId, Address customerAddress) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()
                -> new NotFoundException("Product not found with id " + customerId));

        customerAddress.setCustomer(customer);
        customer.getAddresses().add(customerAddress);
        customerAddressRepository.save(customerAddress);
        customerRepository.save(customer);

        return customer;
    }

}
