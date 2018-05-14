package com.mlxc.controller;

import com.mlxc.entity.Activity;
import com.mlxc.entity.User;
import com.mlxc.service.ActivityService;
import com.mlxc.utils.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private HttpSession session;

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

    @PostMapping("/remove-activity")
    public ResultModel removeActivity(long activityId) {
        ResultModel resultModel = new ResultModel();

        User user = getCurrentUser();

        if (user == null) {
            resultModel.setErrcode(0);
            resultModel.setErrmsg("当前用户未登录");

            return resultModel;
        }
        if (user.getUserType() != 2) {
            resultModel.setErrcode(0);
            resultModel.setErrmsg("只有管理员有权限删除活动");

            return resultModel;
        }

        activityService.removeActivity(activityId);

        resultModel.setErrcode(1);
        resultModel.setErrmsg("删除成功");

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

    private User getCurrentUser() {
        Object user =  session.getAttribute("user");

        if(user == null) {
            return null;
        }

        if(user instanceof User) {
            return (User) user;
        }

        return null;
    }

    public ActivityService getActivityService() {
        return activityService;
    }

    public void setActivityService(ActivityService activityService) {
        this.activityService = activityService;
    }
}
