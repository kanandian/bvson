package com.mlxc.controller;

import com.mlxc.entity.Activity;
import com.mlxc.entity.Commodity;
import com.mlxc.entity.User;
import com.mlxc.entityrelation.UserActivity;
import com.mlxc.entityrelation.UserCommodity;
import com.mlxc.parammodel.LoginModel;
import com.mlxc.parammodel.RegisterModel;
import com.mlxc.service.ActivityService;
import com.mlxc.service.CommodityService;
import com.mlxc.service.UserService;
import com.mlxc.utils.BuyRecord;
import com.mlxc.utils.ResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class UserAdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private CommodityService commodityService;

    @Autowired
    private HttpSession session;

    @PostMapping("/register")
    public ResultModel register(RegisterModel registerModel) {
        User user = registerModel.createUser();
        userService.addUser(user);

        ResultModel resultModel = new ResultModel();
        resultModel.setErrcode(1);
        resultModel.setErrmsg("注册成功");
        resultModel.setData("/index");

        return resultModel;
    }

    @PostMapping("/login")
    public ResultModel login(LoginModel loginModel) {
        ResultModel resultModel = new ResultModel();

        User user = userService.findUserByUserName(loginModel.getUserName());

        if(user == null) {
            resultModel.setErrcode(0);
            resultModel.setErrmsg("用户不存在");
            return resultModel;
        }

        if(user.getPassword().equals(loginModel.getPassword())) {
            resultModel.setErrcode(1);
            resultModel.setErrmsg("登录成功");
            resultModel.setData("/index");

            setCurrentUser(user);

            return resultModel;
        } else {
            resultModel.setErrcode(0);
            resultModel.setErrmsg("密码错误");
            return resultModel;
        }

    }

    @GetMapping("/get-userinfo")
    public ResultModel getUserInfo() {

        ResultModel resultModel = new ResultModel();

        User user = getCurrentUser();

        if(user == null) {
            resultModel.setErrcode(0);
            resultModel.setErrmsg("用户未登录");

            return resultModel;
        } else {
            resultModel.setErrcode(1);
            resultModel.setErrmsg("获取成功");
            resultModel.setData(user);

            return resultModel;
        }

    }

    @PostMapping("/signup-activity")
    public ResultModel signup(long activityId) {
        ResultModel resultModel = new ResultModel();

        User user = getCurrentUser();
        if(user == null) {
            resultModel.setErrcode(0);
            resultModel.setErrmsg("当前用户未登录");

            return resultModel;
        }

        Activity activity = activityService.getActivityById(activityId);
        if(activity.getCurrentNum() >= activity.getNumLimit()) {
            resultModel.setErrcode(0);
            resultModel.setErrmsg("报名人数已满，以无法再报名");

            return resultModel;
        }

        userService.signUpActivity(new UserActivity(user.getUserId(), activityId));

        resultModel.setErrcode(1);
        resultModel.setErrmsg("报名成功");

        return resultModel;
    }

    @GetMapping("/get-attended-activities")
    public ResultModel getAttendedActivities() {
        ResultModel resultModel = new ResultModel();

        User user = getCurrentUser();
        if(user == null) {
            resultModel.setErrcode(0);
            resultModel.setErrmsg("当前用户未登录");

            return resultModel;
        }

        long userId = user.getUserId();

        List<Activity> activityList = userService.getAttendedActivities(userId);

        resultModel.setErrcode(1);
        resultModel.setErrmsg("获取成功");
        resultModel.setData(activityList);

        return  resultModel;
    }


    @PostMapping("/buy-commodity")
    public ResultModel buyCommodity(UserCommodity userCommodity) {
        ResultModel resultModel = new ResultModel();

        User user = getCurrentUser();
        if(user == null) {
            resultModel.setErrcode(0);
            resultModel.setErrmsg("当前用户未登录");

            return resultModel;
        }

        Commodity commodity = commodityService.getCommodityById(userCommodity.getCommodityId());
        if(userCommodity.getNum() > commodity.getRest()) {
            resultModel.setErrcode(0);
            resultModel.setErrmsg("该商品数量不足，购买失败");

            return resultModel;
        }

        double price = commodity.getPrice()*userCommodity.getNum();

        if(price > user.getBalance()) {
            resultModel.setErrcode(0);
            resultModel.setErrmsg("用户余额不足");

            return resultModel;
        }

        userService.buyCommodity(userCommodity, price);

        resultModel.setErrcode(1);
        resultModel.setErrmsg("购买成功");


        return resultModel;
    }

    @GetMapping("/buy-records")
    public ResultModel buyRecords() {
        ResultModel resultModel = new ResultModel();

        User user = getCurrentUser();
        if(user == null) {
            resultModel.setErrcode(0);
            resultModel.setErrmsg("当前用户未登录");

            return resultModel;
        }

        List<BuyRecord> buyRecordList = userService.buyRecords(user.getUserId());

        resultModel.setErrcode(1);
        resultModel.setErrmsg("获取成功");
        resultModel.setData(buyRecordList);

        return resultModel;
    }


    private void setCurrentUser(User user) {
        session.setAttribute("user", user);
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

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public ActivityService getActivityService() {
        return activityService;
    }

    public void setActivityService(ActivityService activityService) {
        this.activityService = activityService;
    }

    public CommodityService getCommodityService() {
        return commodityService;
    }

    public void setCommodityService(CommodityService commodityService) {
        this.commodityService = commodityService;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }
}
