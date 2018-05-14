package com.mlxc.service;

import com.mlxc.entity.Activity;
import com.mlxc.entityrelation.UserActivity;

import java.util.List;

public interface ActivityService {

    public List<Activity> getActivities();
    public Activity getActivityById(long activityId);
    public void addActivity(Activity activity);

    public List<UserActivity> getUserActivitiesByActivityId(long activityId);
    public List<UserActivity> getUserActivitiesByUserId(long userId);

    public void removeActivity(long activityId);

}
