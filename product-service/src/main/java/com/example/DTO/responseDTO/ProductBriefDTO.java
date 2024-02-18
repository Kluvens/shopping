package com.example.DTO.responseDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class ProductBriefDTO {
    private String id;
    private String name;
    private double price;
    private String coverImage;
}
