package com.example.service;

import com.example.DTO.requestDTO.ProductUpdateDTO;
import com.example.DTO.responseDTO.ProductResponseDTO;
import com.example.exception.NotFoundException;
import com.example.model.Category;
import com.example.model.Product;
import com.example.model.ProductAttribute;
import com.example.model.ProductImage;
import com.example.repository.CategoryRepository;
import com.example.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<ProductResponseDTO> listAllProducts() {
        return productRepository.findAll().stream().map(Product::toResponseDto).collect(Collectors.toList());
    }

    public ProductResponseDTO findById(String id) {
        Product product = productRepository.findById(id).orElseThrow(()
                    -> new NotFoundException("Product not found with id " + id));

        return product.toResponseDto();
    }

    public Product createProduct(Product product, String categoryName, List<String> imagesName, List<String> attributesName) {
        product.setCategory(getProductCategoryByName(categoryName))
                .setImageUrls(getProductImagesByName(product, imagesName))
                .setAttributes(getProductAttributesByName(product, attributesName));
        return productRepository.save(product);
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    public ProductResponseDTO updateProduct(String id, ProductUpdateDTO updatedProduct) {
        Product product = productRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Product not found with id " + id));

        product.getImageUrls().clear();
        product.getAttributes().clear();

        product.setName(updatedProduct.getName())
                .setDescription(updatedProduct.getDescription())
                .setPrice(BigDecimal.valueOf(updatedProduct.getPrice()))
                .setCategory(getProductCategoryByName(updatedProduct.getCategory()))
                .setImageUrls(getProductImagesByName(product, updatedProduct.getImageUrls()))
                .setAttributes(getProductAttributesByName(product, updatedProduct.getAttributes()));

        return product.toResponseDto();
    }

    private Category getProductCategoryByName(String categoryName) {

        return categoryRepository.findByName(categoryName).orElseGet(() -> {
            Category newCategory = new Category().setName(categoryName);
            return categoryRepository.save(newCategory);
        });
    }

    private List<ProductImage> getProductImagesByName(Product product, List<String> imagesName) {

        return imagesName.stream().map(image -> new ProductImage().setImageUrl(image).setProduct(product)).collect(Collectors.toList());
    }

    private List<ProductAttribute> getProductAttributesByName(Product product, List<String> attributesName) {

        return attributesName.stream().map(attribute -> new ProductAttribute().setAttribute(attribute).setProduct(product)).collect(Collectors.toList());
    }

}
