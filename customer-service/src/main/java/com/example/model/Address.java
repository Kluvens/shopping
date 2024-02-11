package com.example.model;

import com.example.DTO.responseDTO.CustomerAddressResponseDTO;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String street;

    private String suburb;

    private String state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public CustomerAddressResponseDTO toResponseDTO() {
        return new CustomerAddressResponseDTO()
                .setId(this.id)
                .setStreet(this.street)
                .setSuburb(this.suburb)
                .setState(this.state);
    }

}
