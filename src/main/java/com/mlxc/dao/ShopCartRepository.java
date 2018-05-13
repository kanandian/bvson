package com.mlxc.dao;

import com.mlxc.entity.Commodity;
import com.mlxc.entity.ShoppingCartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopCartRepository extends JpaRepository<ShoppingCartItem, Long> {
    public List<ShoppingCartItem> findByUserId(Long userid);


}
