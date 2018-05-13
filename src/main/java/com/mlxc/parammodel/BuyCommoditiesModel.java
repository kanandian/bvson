package com.mlxc.parammodel;

import com.mlxc.entity.User;
import com.mlxc.entityrelation.UserCommodity;

import java.util.ArrayList;
import java.util.List;

public class BuyCommoditiesModel {

    private String ids;
    private String nums;

    public BuyCommoditiesModel() {

    }

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public List<UserCommodity> getUserCommodityList() {

        String[] commodityIds = ids.split("[,]");
        String[] counts = nums.split("[,]");

        List<UserCommodity> userCommodityList = new ArrayList<UserCommodity>();

        for (int i=0;i<commodityIds.length;i++) {
            long commodityId = Long.parseLong(commodityIds[i]);
            int num = Integer.parseInt(counts[i]);

            UserCommodity userCommodity = new UserCommodity();
            userCommodity.setCommodityId(commodityId);
            userCommodity.setNum(num);

            userCommodityList.add(userCommodity);

        }


        return userCommodityList;
    }
}
