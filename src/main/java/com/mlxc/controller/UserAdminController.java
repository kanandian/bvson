package com.mlxc.controller;

import com.mlxc.entity.Activity;
import com.mlxc.entity.Commodity;
import com.mlxc.entity.ShoppingCartItem;
import com.mlxc.entity.User;
import com.mlxc.entityrelation.UserActivity;
import com.mlxc.entityrelation.UserCommodity;
import com.mlxc.parammodel.*;
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
import javax.xml.ws.soap.Addressing;
import java.util.ArrayList;
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

        login(new LoginModel(registerModel.getUserName(), registerModel.getPassword()));

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

    @GetMapping("/get-users-admin")
    public ResultModel getUsersAdmin() {
        ResultModel resultModel = new ResultModel();

        User user = getCurrentUser();

        if (user == null) {
            resultModel.setErrcode(0);
            resultModel.setErrmsg("用户未登录！");
            return resultModel;
        }
        resultModel.setErrcode(1);
        resultModel.setErrmsg("成功");
        List<User> userList = new ArrayList<User>();
        if (user.getUserType() == 2) {
            userList = userService.findUserByUserType(1);
        }
        resultModel.setData(userList);

        return resultModel;
    }

    @PostMapping("/update_user_info")
    public ResultModel updateUserInfo(UpdateInfoModel updateInfoModel) {
        ResultModel resultModel = new ResultModel();

        User user = getCurrentUser();

        if (user == null) {
            resultModel.setErrcode(0);
            resultModel.setErrmsg("用户未登录！");
            return resultModel;
        }

        user.setEmail(updateInfoModel.getEmail());
        user.setPhoneNumber(updateInfoModel.getPhoneNumber());

        userService.addUser(user);
        resultModel.setErrcode(1);

        return resultModel;
    }

    @PostMapping("/update_user_password")
    public ResultModel updateUserPassword(UpdatePasswordModel updatePasswordModel) {
        ResultModel resultModel = new ResultModel();

        User user = getCurrentUser();

        if (user == null) {
            resultModel.setErrcode(0);
            resultModel.setErrmsg("用户未登录！");
            return resultModel;
        }

        if (user.getPassword().equals(updatePasswordModel.getOpassword())) {
            user.setPassword(updatePasswordModel.getNpassword());

            userService.addUser(user);
            resultModel.setErrcode(1);
            return resultModel;
        } else {
            resultModel.setErrcode(0);
            resultModel.setErrmsg("原始密码输入不正确");
            return resultModel;
        }
    }

    @PostMapping("/remove-user")
    public ResultModel removeUser(long userId) {
        ResultModel resultModel = new ResultModel();

        User user = getCurrentUser();
        if (user.getUserType() != 2) {
            resultModel.setErrcode(0);
            resultModel.setErrmsg("只有管理员有权限删除用户");
            return resultModel;
        }

        userService.removeUser(userId);

        resultModel.setErrcode(1);
        resultModel.setErrmsg("删除成功");
        return resultModel;
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

        List<Activity> activityList = userService.getAttendedActivities(user.getUserId());

        for (Activity activity1 : activityList) {
            if (activity1.getActivityId() == activityId) {
                resultModel.setErrcode(0);
                resultModel.setErrmsg("您已报名参加过该活动");

                return resultModel;
            }
        }

        userService.signUpActivity(new UserActivity(user.getUserId(), activityId, activity.getImageURL(), activity.getActivityName(), user.getUserName(), user.getPhoneNumber(), activity.getCreateTime(), activity.getStartTime(), activity.getDes()));

        resultModel.setErrcode(1);
        resultModel.setErrmsg("报名成功");

        return resultModel;
    }


    @PostMapping("/update-useractivity-info")
    public ResultModel updateUserActivityInfo(UpdateUserActivityInfoModel updateUserActivityInfoModel) {
        ResultModel resultModel = new ResultModel();

        UserActivity userActivity = userService.getUserActivityById(updateUserActivityInfoModel.getId());
        userActivity.setUserName(updateUserActivityInfoModel.getUserName());
        userActivity.setMobile(updateUserActivityInfoModel.getMobile());
        userService.updateUserActivity(userActivity);

        resultModel.setErrcode(1);
        resultModel.setErrmsg("修改成功");

        return  resultModel;
    }

    @GetMapping("/get-useractivity-info")
    public ResultModel getUserActivityInfo(long id) {
        ResultModel resultModel = new ResultModel();

        UserActivity userActivity = userService.getUserActivityById(id);

        resultModel.setErrcode(1);
        resultModel.setErrmsg("获取成功");
        resultModel.setData(userActivity);

        return  resultModel;
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

        List<UserActivity> activityList = activityService.getUserActivitiesByUserId(user.getUserId());

        resultModel.setErrcode(1);
        resultModel.setErrmsg("获取成功");
        resultModel.setData(activityList);

        return  resultModel;
    }


    @GetMapping("/get-shop-cart")
    public ResultModel getShopCart() {
        ResultModel resultModel = new ResultModel();

        User user = getCurrentUser();
        if (user == null) {
            resultModel.setErrcode(0);
            resultModel.setErrmsg("用户未登录");

            return resultModel;
        }

        List<ShoppingCartItem> shoppingCartItems = userService.getShopCart(user.getUserId());

        resultModel.setErrcode(1);
        resultModel.setErrmsg("获取成功");

        resultModel.setData(shoppingCartItems);

        return resultModel;
    }

    @PostMapping("/add-shop-cart")
    public ResultModel addShopCart(AddShopCartModel addShopCartModel) {
        ResultModel resultModel = new ResultModel();

        User user = getCurrentUser();
        if (user == null) {
            resultModel.setErrcode(0);
            resultModel.setErrmsg("用户未登录");

            return resultModel;
        }

        Commodity commodity = commodityService.getCommodityById(addShopCartModel.getCommodityId());
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();

        shoppingCartItem.setCommodityId(commodity.getCommodityId());
        shoppingCartItem.setCommodityName(commodity.getCommodityName());
        shoppingCartItem.setCount(addShopCartModel.getNum());
        shoppingCartItem.setPrice(commodity.getPrice());
        shoppingCartItem.setUserId(user.getUserId());
        shoppingCartItem.setImageURL(commodity.getImageURL());

        userService.addShopCart(shoppingCartItem);

        resultModel.setErrcode(1);
        resultModel.setErrmsg("添加成功");

        return resultModel;
    }

    @PostMapping("/remove-shop-cart")
    public ResultModel removeShopCart(long cartId) {
        ResultModel resultModel = new ResultModel();

        User user = getCurrentUser();
        if (user == null) {
            resultModel.setErrcode(0);
            resultModel.setErrmsg("用户未登录");

            return resultModel;
        }

        userService.removeShopCart(cartId);

        resultModel.setErrcode(1);
        resultModel.setErrmsg("成功");

        return resultModel;
    }


    @PostMapping("/buy-commodities")
    public ResultModel buyCommodities(BuyCommoditiesModel buyCommoditiesModel) {
        ResultModel resultModel = new ResultModel();

        User user = getCurrentUser();
        if(user == null) {
            resultModel.setErrcode(0);
            resultModel.setErrmsg("当前用户未登录");

            return resultModel;
        }

        List<UserCommodity> userCommodityList = buyCommoditiesModel.getUserCommodityList();
        for (UserCommodity userCommodity : userCommodityList) {
            userCommodity.setUserId(user.getUserId());

            Commodity commodity = commodityService.getCommodityById(userCommodity.getCommodityId());
            userCommodity.setCommodityName(commodity.getCommodityName());
            userCommodity.setPrice(commodity.getPrice());
            userCommodity.setImageURL(commodity.getImageURL());
            userCommodity.setHolderId(commodity.getUserid());
        }

        session.setAttribute("userCommodityList", userCommodityList);

        resultModel.setErrcode(1);
        resultModel.setErrmsg("成功");

        return resultModel;
    }

    @GetMapping("/get-payment")
    public ResultModel getPayment() {
        ResultModel resultModel = new ResultModel();

        User user = getCurrentUser();
        if(user == null) {
            resultModel.setErrcode(0);
            resultModel.setErrmsg("当前用户未登录");

            return resultModel;
        }

        List<UserCommodity> userCommodityList = (List<UserCommodity>) session.getAttribute("userCommodityList");


        resultModel.setErrcode(1);
        resultModel.setErrmsg("成功");
        resultModel.setData(userCommodityList);

        return resultModel;
    }

    @PostMapping("/payment")
    public ResultModel payment(PaymentModel paymentModel) {
        ResultModel resultModel = new ResultModel();

        User user = getCurrentUser();

        List<UserCommodity> userCommodityList = (List<UserCommodity>) session.getAttribute("userCommodityList");

        for (UserCommodity userCommodity : userCommodityList) {
            userCommodity.setUserName(paymentModel.getUserName());
            userCommodity.setMobile(paymentModel.getMobile());
            userCommodity.setDes(paymentModel.getDes());
            userCommodity.setAddress(paymentModel.getAddress());
        }

        double totalPrice = 0;
        for (UserCommodity userCommodity : userCommodityList) {
            Commodity commodity = commodityService.getCommodityById(userCommodity.getCommodityId());

            if(userCommodity.getNum() > commodity.getRest()) {
                resultModel.setErrcode(0);
                resultModel.setErrmsg("该商品数量不足，购买失败");

                return resultModel;
            }

            double price = commodity.getPrice()*userCommodity.getNum();
            totalPrice += price;
        }

        if(totalPrice > user.getBalance()) {
            resultModel.setErrcode(0);
            resultModel.setErrmsg("用户余额不足");

            return resultModel;
        }


        userService.buyCommodities(userCommodityList, totalPrice);

        resultModel.setErrcode(1);
        resultModel.setErrmsg("购买成功");

        return resultModel;
    }

    @PostMapping("/comfirm-receipt")
    public ResultModel confirmReceipt(OrderStatusAdminModel orderStatusAdminModel) {
        ResultModel resultModel = new ResultModel();

        User user = getCurrentUser();
        if(user == null) {
            resultModel.setErrcode(0);
            resultModel.setErrmsg("当前用户未登录");

            return resultModel;
        }

        orderStatusAdminModel.setStatus(2);

        userService.updateUserCommodityStatus(orderStatusAdminModel.getId(), orderStatusAdminModel.getStatus());

        resultModel.setErrcode(1);
        resultModel.setErrmsg("成功");

        return resultModel;
    }

    @PostMapping("/distribution")
    public ResultModel distribution(OrderStatusAdminModel orderStatusAdminModel) {
        ResultModel resultModel = new ResultModel();

        User user = getCurrentUser();
        if(user == null) {
            resultModel.setErrcode(0);
            resultModel.setErrmsg("当前用户未登录");

            return resultModel;
        }
        if (user.getUserType() != 1) {
            resultModel.setErrcode(0);
            resultModel.setErrmsg("只有商家能发货");

            return resultModel;
        }

        orderStatusAdminModel.setStatus(1);

        userService.updateUserCommodityStatus(orderStatusAdminModel.getId(), orderStatusAdminModel.getStatus());

        resultModel.setErrcode(1);
        resultModel.setErrmsg("成功");

        return resultModel;
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

        userCommodity.setUserId(user.getUserId());

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

    @GetMapping("/sold-records")
    public ResultModel soldRecords() {
        ResultModel resultModel = new ResultModel();

        User user = getCurrentUser();

        if(user == null) {
            resultModel.setErrcode(0);
            resultModel.setErrmsg("当前用户未登录");

            return resultModel;
        }
        if (user.getUserType() != 1) {
            resultModel.setErrcode(0);
            resultModel.setErrmsg("只有商家可以查看");

            return resultModel;
        }

        List<UserCommodity> userCommodityList = userService.getUserCommoditiesByHolderId(user.getUserId());

        resultModel.setErrcode(1);
        resultModel.setErrmsg("获取成功");
        resultModel.setData(userCommodityList);

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
            return userService.findUserById(((User) user).getUserId());
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
