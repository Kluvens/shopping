package com.example.service;

import com.example.DTO.requestDTO.CustomerAddressCreationDTO;
import com.example.DTO.responseDTO.CustomerAddressResponseDTO;
import com.example.DTO.responseDTO.CustomerResponseDTO;
import com.example.exception.NotFoundException;
import com.example.model.Address;
import com.example.model.Customer;
import com.example.repository.CustomerAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerAddressService {
    private final CustomerAddressRepository customerAddressRepository;

    public CustomerAddressResponseDTO findById(String id) {
        Address address = customerAddressRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Product not found with id " + id));

        return address.toResponseDTO();
    }

    public Address createCustomerAddress(CustomerAddressCreationDTO customerAddressCreationDTO) {
        return customerAddressRepository.save(customerAddressCreationDTO.toEntity());
    }
}
