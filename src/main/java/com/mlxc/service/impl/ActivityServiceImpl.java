package com.mlxc.service.impl;

import com.mlxc.dao.ActivityRepository;
import com.mlxc.entity.Activity;
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

    public ActivityRepository getActivityRepository() {
        return activityRepository;
    }

    public void setActivityRepository(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }
}
