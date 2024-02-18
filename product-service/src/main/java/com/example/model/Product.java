package com.example.model;

import com.example.DTO.responseDTO.ProductResponseDTO;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name = "product";

    private String description = "There is no description yet";

    private BigDecimal price = BigDecimal.valueOf(0);

    @OneToMany(mappedBy = "product", cascade = {CascadeType.ALL})
    private List<ProductImage> imageUrls = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = {CascadeType.ALL})
    private List<ProductAttribute> attributes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category = new Category().setName("undefined");

    public ProductResponseDTO toResponseDto() {
        return new ProductResponseDTO()
                .setId(this.id)
                .setName(this.name)
                .setPrice(this.price.doubleValue())
                .setDescription(this.description)
                .setCategory(this.category.getName())
                .setImageUrls(this.imageUrls.stream().map(ProductImage::getImageUrl).collect(Collectors.toList()))
                .setAttributes(this.attributes.stream().map(ProductAttribute::getAttribute).collect(Collectors.toList()));
    }
}
