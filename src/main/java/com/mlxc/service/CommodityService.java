package com.mlxc.service;

import com.mlxc.entity.Commodity;

import java.util.List;

public interface CommodityService {

    public List<Commodity> getCommodities();
    public Commodity getCommodityById(long commodityId);
    public void addCommodity(Commodity commodity);

}
