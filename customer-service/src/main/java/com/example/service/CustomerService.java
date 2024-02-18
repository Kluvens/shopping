package com.example.service;

import com.example.DTO.requestDTO.CustomerAddressCreationDTO;
import com.example.DTO.requestDTO.CustomerCreationDTO;
import com.example.DTO.requestDTO.CustomerUpdateDTO;
import com.example.DTO.responseDTO.CustomerResponseDTO;
import com.example.DTO.responseDTO.ProductBriefDTO;
import com.example.exception.NotFoundException;
import com.example.model.Address;
import com.example.model.Customer;
import com.example.model.FavouriteItem;
import com.example.repository.CustomerAddressRepository;
import com.example.repository.CustomerRepository;
import com.example.repository.FavouriteItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerAddressRepository customerAddressRepository;
    private final FavouriteItemRepository favouriteItemRepository;
    private final ProductFeignClient productFeignClient;

    public List<CustomerResponseDTO> listAllCustomers() {
        return customerRepository.findAll().stream().map(Customer::toResponseDTO).collect(Collectors.toList());
    }

    public CustomerResponseDTO findById(String id) {
        Customer customer = customerRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Customer not found with id " + id));

        return customer.toResponseDTO();
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }

    public Customer updateCustomer(String id, CustomerUpdateDTO customerUpdateDTO) {
        Customer customer = customerRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Customer not found with id " + id));

        customer.setUsername(customerUpdateDTO.getUsername())
                .setEmail(customerUpdateDTO.getEmail())
                .setPassword(customerUpdateDTO.getPassword());

        return customer;
    }

    public Customer addAddressToCustomer(String id, CustomerAddressCreationDTO customerAddressCreationDTO) {
        Customer customer = customerRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Customer not found with id " + id));

        List<Address> addresses = customer.getAddresses();

        addresses.add(new Address()
                .setCustomer(customer)
                .setStreet(customerAddressCreationDTO.getStreet())
                .setSuburb(customerAddressCreationDTO.getSuburb())
                .setState(customerAddressCreationDTO.getState()));

        return customer;
    }

    public Customer removeAddressFromCustomer(String id, String addressId) {
        Customer customer = customerRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Customer not found with id " + id));

        Address address = customerAddressRepository.findById(addressId).orElseThrow(()
                -> new NotFoundException("Address not found with id " + id));

        customer.getAddresses().remove(address);
        customerAddressRepository.deleteById(addressId);

        return customer;
    }

    public List<ProductBriefDTO> getFavouriteItems(String id) {
        Customer customer = customerRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Customer not found with id " + id));

        List<FavouriteItem> favouriteItems = customer.getFavouriteItems();

        return favouriteItems.stream().map(item -> productFeignClient.getBriefProductById(item.getProductId())).collect(Collectors.toList());
    }

    public CustomerResponseDTO addToFavouriteList(String id, String productId) {
        Customer customer = customerRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Customer not found with id " + id));

        Set<String> productIds = new HashSet<>();
        customer.getFavouriteItems().forEach(item -> productIds.add(item.getProductId()));

        if (!productIds.contains(productId)) {
            customer.getFavouriteItems().add(new FavouriteItem().setCustomer(customer).setProductId(productId));
        }

        return customer.toResponseDTO();
    }

    public CustomerResponseDTO removeFromFavouriteList(String id, String productId) {
        Customer customer = customerRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Customer not found with id " + id));

        FavouriteItem favouriteItem = favouriteItemRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Product not found with id " + id));

        customer.getFavouriteItems().remove(favouriteItemRepository.findByProductId(productId));
        favouriteItemRepository.deleteById(favouriteItem.getId());

        return customer.toResponseDTO();
    }

}
