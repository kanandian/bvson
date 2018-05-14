package com.mlxc.entityrelation;

import org.omg.PortableServer.THREAD_POLICY_ID;
import sun.jvm.hotspot.jdi.PrimitiveValueImpl;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_activity")
public class UserActivity {

    private Long id;
    private Long userId;
    private Long activityId;
    private String imageURL;
    private String activityName;
    private String userName;
    private String mobile;
    private long createTime;
    private long startTime;
    private String des;

    public UserActivity() {
    }

    public UserActivity(Long userId, Long activityId) {
        this.userId = userId;
        this.activityId = activityId;
    }

    public UserActivity(Long userId, Long activityId, String imageURL, String activityName, String userName, String mobile, long createTime, long startTime, String des) {
        this.userId = userId;
        this.activityId = activityId;
        this.imageURL = imageURL;
        this.activityName = activityName;
        this.userName = userName;
        this.mobile = mobile;
        this.createTime = createTime;
        this.startTime = startTime;
        this.des = des;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
