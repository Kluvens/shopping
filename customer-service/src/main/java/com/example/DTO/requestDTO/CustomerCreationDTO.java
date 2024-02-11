package com.example.DTO.requestDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CustomerCreationDTO {
    private String username;
    private String email;
    private String password;

}
