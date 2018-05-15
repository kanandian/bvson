package com.mlxc.service;

import com.mlxc.entity.Activity;
import com.mlxc.entity.ShoppingCartItem;
import com.mlxc.entity.User;
import com.mlxc.entityrelation.UserActivity;
import com.mlxc.entityrelation.UserCommodity;
import com.mlxc.utils.BuyRecord;

import java.util.List;

public interface UserService {

    public void addUser(User user);

    public User findUserById(long id);

    public User findUserByUserName(String userName);

    public void signUpActivity(UserActivity userActivity);

    public List<Activity> getAttendedActivities(long userId);

    public void buyCommodity(UserCommodity userCommodity, double price);

    public void buyCommodities(List<UserCommodity> userCommodityList, double totalPrice);

    public List<BuyRecord> buyRecords(long userId);

    public void addShopCart(ShoppingCartItem shoppingCartItem);

    public void removeShopCart(long cartId);

    public List<ShoppingCartItem> getShopCart(long userId);

    public UserActivity getUserActivityById(long id);

    public void updateUserActivity(UserActivity userActivity);

    public List<UserCommodity> getUserCommoditiesByHolderId(long holderId);

}
