package com.example.service;

import com.example.DTO.responseDTO.ProductBriefDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "http://localhost:8080/api/products")
public interface ProductFeignClient {
    @GetMapping("/{id}/brief")
    ProductBriefDTO getBriefProductById(@PathVariable String id);

}
