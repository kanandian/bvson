package com.mlxc.entity;

import javax.persistence.*;

@Entity
@Table(name = "activity")
public class Activity {

    private long activityId;
    private String activityName;
    private String des;
    private int numLimit;
    private int currentNum;

    public Activity() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getNumLimit() {
        return numLimit;
    }

    public void setNumLimit(int numLimit) {
        this.numLimit = numLimit;
    }

    public int getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(int currentNum) {
        this.currentNum = currentNum;
    }
}
