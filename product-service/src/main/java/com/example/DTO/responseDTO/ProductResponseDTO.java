package com.example.DTO.responseDTO;

import com.example.model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class ProductResponseDTO {
    private String id;
    private String name;
    private String description;
    private double price;
    private String category;
    private List<String> imageUrls = new ArrayList<>();
    private List<String> attributes = new ArrayList<>();

}
