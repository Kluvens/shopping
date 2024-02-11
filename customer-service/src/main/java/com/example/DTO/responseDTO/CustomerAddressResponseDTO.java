package com.example.DTO.responseDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class CustomerAddressResponseDTO {
    private String id;
    private String street;
    private String suburb;
    private String state;

}
