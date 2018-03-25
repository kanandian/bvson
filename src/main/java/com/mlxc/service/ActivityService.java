package com.mlxc.service;

import com.mlxc.entity.Activity;

import java.util.List;

public interface ActivityService {

    public List<Activity> getActivities();
    public Activity getActivityById(long activityId);
    public void addActivity(Activity activity);

}
