package com.example.DTO.responseDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class CustomerResponseDTO {
    private String id;
    private String username;
    private String email;
    private String password;
    private String profileImage;
    private String bio;
    private List<CustomerAddressResponseDTO> addresses;

}
