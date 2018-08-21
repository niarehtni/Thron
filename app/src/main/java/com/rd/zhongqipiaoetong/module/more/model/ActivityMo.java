package com.rd.zhongqipiaoetong.module.more.model;

import com.rd.zhongqipiaoetong.network.entity.PageMo;
import com.rd.zhongqipiaoetong.utils.ActivityUtils;
import com.rd.zhongqipiaoetong.utils.DensityUtils;

import java.util.List;

/**
 * 精彩活动列表
 */
public class ActivityMo extends PageMo {

    private String qiniuDomain;
    private List<Activity> activityList;

    public String getQiniuDomain() {
        return qiniuDomain;
    }

    public void setQiniuDomain(String qiniuDomain) {
        this.qiniuDomain = qiniuDomain;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }

    public class Activity{
        private String imageUrl;
        private String url;
        private String title;
        private String imageHost;

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setImageHost(String imageHost) {
            this.imageHost = imageHost;
        }

        public String getWholeImageUrl(){
            return imageHost + imageUrl;
        }

        public int getHeight(){
            return (int) (DensityUtils.getWidth(ActivityUtils.peek()) / 750f * 320 + 0.5f);
        }
    }
}
