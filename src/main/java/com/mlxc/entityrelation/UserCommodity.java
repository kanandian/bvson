package com.mlxc.entityrelation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_commodity")
public class UserCommodity {

    private long id;
    private long userId;
    private long commodityId;
    private int num;

    public UserCommodity() {
    }

    public UserCommodity(long userId, long commodityId, int num) {
        this.userId = userId;
        this.commodityId = commodityId;
        this.num = num;
    }

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(long commodityId) {
        this.commodityId = commodityId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
