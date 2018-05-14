package com.mlxc.service.impl;

import com.mlxc.dao.ActivityRepository;
import com.mlxc.dao.UserActivityRepository;
import com.mlxc.entity.Activity;
import com.mlxc.entityrelation.UserActivity;
import com.mlxc.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Component
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserActivityRepository userActivityRepository;

    @Override
    @Transactional
    public List<Activity> getActivities() {
        return activityRepository.findAll();
    }

    @Override
    public Activity getActivityById(long activityId) {
        return activityRepository.findOne(activityId);
    }

    @Override
    @Transactional
    public void addActivity(Activity activity) {
        activityRepository.save(activity);
    }

    @Override
    @Transactional
    public List<UserActivity> getUserActivitiesByActivityId(long activityId) {
        return userActivityRepository.findByActivityId(activityId);
    }

    @Override
    public List<UserActivity> getUserActivitiesByUserId(long userId) {
        return userActivityRepository.findByUserId(userId);
    }

    @Override
    public void removeActivity(long activityId) {
        activityRepository.delete(activityId);
    }

    public ActivityRepository getActivityRepository() {
        return activityRepository;
    }

    public void setActivityRepository(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }
}
