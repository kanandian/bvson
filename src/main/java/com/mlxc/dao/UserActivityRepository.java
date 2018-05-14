package com.mlxc.dao;

import com.mlxc.entityrelation.UserActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserActivityRepository extends JpaRepository<UserActivity, Long> {

    public List<UserActivity> findByUserId(Long userId);
    public List<UserActivity> findByActivityId(Long activityId);

}
