package com.mlxc.controller;

import com.mlxc.entity.Activity;
import com.mlxc.service.ActivityService;
import com.mlxc.utils.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping("/get-activities")
    public ResultModel getActivities() {
        ResultModel resultModel = new ResultModel();

        resultModel.setErrcode(1);
        resultModel.setErrmsg("成功");
        resultModel.setData(activityService.getActivities());

        return resultModel;
    }

    @PostMapping("/add-activity")
    public ResultModel addActivity(Activity activity) {
        ResultModel resultModel = new ResultModel();

        activityService.addActivity(activity);

        resultModel.setErrcode(1);
        resultModel.setErrmsg("添加成功");

        return resultModel;
    }

    @GetMapping("/get-activity-info")
    public ResultModel getActivityInfo(long activityId) {
        ResultModel resultModel = new ResultModel();


        Activity activity = activityService.getActivityById(activityId);

        resultModel.setErrcode(1);
        resultModel.setErrmsg("成功");
        resultModel.setData(activity);


        return resultModel;
    }

    @GetMapping("/test")
    public String test() {
        return "wohenshaui";
    }

    public ActivityService getActivityService() {
        return activityService;
    }

    public void setActivityService(ActivityService activityService) {
        this.activityService = activityService;
    }
}
