package com.mlxc.dao;

import com.mlxc.entityrelation.UserCommodity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCommodityReposity extends JpaRepository<UserCommodity, Long> {

    public List<UserCommodity> findByUserId(Long userId);
    public List<UserCommodity> findByHolderId(Long holder);


}
