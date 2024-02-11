package com.example.repository;

import com.example.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerAddressRepository extends JpaRepository<Address, String> {
}
