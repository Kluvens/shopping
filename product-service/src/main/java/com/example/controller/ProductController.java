package com.example.controller;

import com.example.DTO.requestDTO.ProductCreationDTO;
import com.example.DTO.requestDTO.ProductUpdateDTO;
import com.example.DTO.responseDTO.ProductResponseDTO;
import com.example.model.Product;
import com.example.service.ProductService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<ProductResponseDTO> getAllProducts() {
        return productService.listAllProducts();
    }

    @GetMapping("/{id}")
    @CircuitBreaker(name = "product")
    @TimeLimiter(name = "product")
    @Retry(name = "product")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable String id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductCreationDTO productCreationDTO) {
        Product newProduct = productCreationDTO.toEntity();
        String category = productCreationDTO.getCategory();
        List<String> imageUrls = productCreationDTO.getImageUrls();
        List<String> attributes = productCreationDTO.getAttributes();
        Product createdProduct = productService.createProduct(newProduct, category, imageUrls, attributes);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/products/{id}")
                .buildAndExpand(createdProduct.getId()).toUri();

        return ResponseEntity.created(location).body(createdProduct.toResponseDto());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable String id, @RequestBody ProductUpdateDTO productUpdateDTO) {
        ProductResponseDTO updatedProduct = productService.updateProduct(id, productUpdateDTO);
        return ResponseEntity.ok(updatedProduct);
    }

}
