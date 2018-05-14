package com.mlxc.service.impl;

import com.mlxc.dao.*;
import com.mlxc.entity.Activity;
import com.mlxc.entity.Commodity;
import com.mlxc.entity.ShoppingCartItem;
import com.mlxc.entity.User;
import com.mlxc.entityrelation.UserActivity;
import com.mlxc.entityrelation.UserCommodity;
import com.mlxc.service.UserService;
import com.mlxc.utils.BuyRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserActivityRepository userActivityRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserCommodityReposity userCommodityReposity;

    @Autowired
    private CommodityRepository commodityRepository;

    @Autowired
    private ShopCartRepository shopCartRepository;


    @Override
    @Transactional
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public User findUserById(long id) {
        return userRepository.findOne(id);
    }

    @Override
    @Transactional
    public User findUserByUserName(String userName) {
        User user = userRepository.findByUserName(userName);

//        if(userList.isEmpty()) {
//            return null;
//        }

        return user;
    }

    @Override
    @Transactional
    public void signUpActivity(UserActivity userActivity) {
        Activity activity = activityRepository.findOne(userActivity.getActivityId());
        activity.setCurrentNum(activity.getCurrentNum()+1);

        activityRepository.save(activity);

        userActivityRepository.save(userActivity);
    }

    @Override
    @Transactional
    public List<Activity> getAttendedActivities(long userId) {
        List<UserActivity> userActivityList = userActivityRepository.findByUserId(userId);

        List<Activity> activityList = new ArrayList<Activity>();

        for(UserActivity userActivity : userActivityList) {
            activityList.add(activityRepository.findOne(userActivity.getActivityId()));
        }

        return activityList;
    }

    @Override
    @Transactional
    public void buyCommodity(UserCommodity userCommodity, double price) {
        Commodity commodity = commodityRepository.findOne(userCommodity.getCommodityId());
        commodity.setRest(commodity.getRest()-userCommodity.getNum());
        commodityRepository.save(commodity);

        User user = userRepository.findOne(userCommodity.getUserId());
        user.setBalance(user.getBalance()-price);
        userRepository.save(user);

        User merchant = userRepository.findOne(commodity.getUserid());
        merchant.setBalance(merchant.getBalance()+price);
        userRepository.save(merchant);

        userCommodityReposity.save(userCommodity);
    }

    @Override
    @Transactional
    public void buyCommodities(List<UserCommodity> userCommodityList, double totalPrice) {
        for (UserCommodity userCommodity : userCommodityList) {
            Commodity commodity = commodityRepository.findOne(userCommodity.getCommodityId());
            commodity.setRest(commodity.getRest()-userCommodity.getNum());
            commodityRepository.save(commodity);

            double price = commodity.getPrice()*userCommodity.getNum();
            User merchant = userRepository.findOne(commodity.getUserid());
            merchant.setBalance(merchant.getBalance()+price);
            userRepository.save(merchant);

            userCommodityReposity.save(userCommodity);
        }

        if (userCommodityList != null && !userCommodityList.isEmpty()) {
            User user = userRepository.findOne(userCommodityList.get(0).getUserId());
            user.setBalance(user.getBalance()-totalPrice);
            userRepository.save(user);
        }
    }

    @Override
    @Transactional
    public List<BuyRecord> buyRecords(long userId) {
        List<UserCommodity> userCommodityList = userCommodityReposity.findByUserId(userId);

        List<BuyRecord> buyRecordList = new ArrayList<BuyRecord>();

        for(UserCommodity userCommodity : userCommodityList) {
            Commodity commodity = commodityRepository.findOne(userCommodity.getCommodityId());

            BuyRecord buyRecord = new BuyRecord();
            buyRecord.setCommodityName(commodity.getCommodityName());
            buyRecord.setImageURL(commodity.getImageURL());
            buyRecord.setRest(commodity.getRest());
            buyRecord.setPrice(commodity.getPrice());
            buyRecord.setNum(userCommodity.getNum());
            buyRecord.setDes(commodity.getDes());

            buyRecordList.add(buyRecord);
        }

        return buyRecordList;
    }

    @Override
    public void addShopCart(ShoppingCartItem shoppingCartItem) {
        shopCartRepository.save(shoppingCartItem);
    }

    @Override
    public void removeShopCart(long cartId) {
        shopCartRepository.delete(cartId);
    }

    @Override
    public List<ShoppingCartItem> getShopCart(long userId) {
        List<ShoppingCartItem> shoppingCartItems = shopCartRepository.findByUserId(userId);
        return shoppingCartItems;
    }

    @Override
    public UserActivity getUserActivityById(long id) {
        return userActivityRepository.findOne(id);
    }

    @Override
    public void updateUserActivity(UserActivity userActivity) {
        userActivityRepository.save(userActivity);
    }


    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserActivityRepository getUserActivityRepository() {
        return userActivityRepository;
    }

    public void setUserActivityRepository(UserActivityRepository userActivityRepository) {
        this.userActivityRepository = userActivityRepository;
    }

    public ActivityRepository getActivityRepository() {
        return activityRepository;
    }

    public void setActivityRepository(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public UserCommodityReposity getUserCommodityReposity() {
        return userCommodityReposity;
    }

    public void setUserCommodityReposity(UserCommodityReposity userCommodityReposity) {
        this.userCommodityReposity = userCommodityReposity;
    }

    public CommodityRepository getCommodityRepository() {
        return commodityRepository;
    }

    public void setCommodityRepository(CommodityRepository commodityRepository) {
        this.commodityRepository = commodityRepository;
    }
}
