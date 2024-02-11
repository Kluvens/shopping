package com.example.DTO.responseDTO;

import com.example.model.Category;
import com.example.model.Product;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Accessors(chain = true)
@ToString(callSuper = true)
public class CategoryDTO {
    private String id;
    private String name;
    private List<String> products;

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.products = category.getProducts().stream().map(Product::getName).collect(Collectors.toList());
    }
}
