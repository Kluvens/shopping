package com.example.model;

import com.example.DTO.responseDTO.CustomerResponseDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String username = "customer";

    @Email
    private String email;

    private String password;

    private String profileImage = "";

    private String bio = "";

    @OneToMany(mappedBy = "customer", cascade = {CascadeType.ALL})
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = {CascadeType.ALL})
    private List<FavouriteItem> favouriteItems = new ArrayList<>();

    public CustomerResponseDTO toResponseDTO() {
        return new CustomerResponseDTO()
                .setId(this.id)
                .setUsername(this.username)
                .setEmail(this.email)
                .setPassword(this.password)
                .setProfileImage(this.profileImage)
                .setBio(this.bio)
                .setAddresses(this.addresses.stream().map(Address::toResponseDTO).collect(Collectors.toList()));
    }
}
