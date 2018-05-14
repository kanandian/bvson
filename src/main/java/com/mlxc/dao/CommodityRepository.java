package com.mlxc.dao;

import com.mlxc.entity.Commodity;
import com.mlxc.entityrelation.UserCommodity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommodityRepository extends JpaRepository<Commodity, Long> {

    public List<Commodity> findByUserid(Long userid);


}
