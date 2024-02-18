package com.example.DTO.requestDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class CustomerUpdateDTO {
    private String username;
    private String email;
    private String password;
    private List<CustomerAddressCreationDTO> addresses;
}
