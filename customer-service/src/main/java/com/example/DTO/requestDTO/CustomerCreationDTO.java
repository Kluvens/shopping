package com.example.DTO.requestDTO;

import com.example.model.Customer;
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

    public Customer toEntity() {
        return new Customer()
                .setUsername(this.username)
                .setEmail(this.email)
                .setPassword(this.password);
    }
}
