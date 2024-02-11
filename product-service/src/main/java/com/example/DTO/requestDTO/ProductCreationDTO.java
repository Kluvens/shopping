package com.example.DTO.requestDTO;

import com.example.model.Product;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@ToString(callSuper = true)
public class ProductCreationDTO {
    private String name;
    private double price;
    private String description;
    private List<String> imageUrls;
    private String category;
    private List<String> attributes;

    public Product toEntity() {
        return new Product()
                .setName(this.name)
                .setPrice(BigDecimal.valueOf(this.price))
                .setDescription(this.description);
    }
}
