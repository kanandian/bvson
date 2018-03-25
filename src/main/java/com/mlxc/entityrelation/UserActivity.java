package com.mlxc.entityrelation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_activity")
public class UserActivity {

    private Long id;
    private Long userId;
    private Long activityId;

    public UserActivity() {
    }

    public UserActivity(Long userId, Long activityId) {
        this.userId = userId;
        this.activityId = activityId;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }
}
