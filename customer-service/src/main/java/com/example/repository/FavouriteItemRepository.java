package com.example.repository;

import com.example.model.FavouriteItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavouriteItemRepository extends JpaRepository<FavouriteItem, String> {
    FavouriteItem findByProductId(String productId);
}
