package com.example.DTO.requestDTO;

import com.example.model.Address;
import com.example.model.Customer;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CustomerAddressCreationDTO {
    private String street;
    private String suburb;
    private String state;

    public Address toEntity() {
        return new Address()
                .setStreet(this.street)
                .setSuburb(this.suburb)
                .setState(this.state);
    }
}
