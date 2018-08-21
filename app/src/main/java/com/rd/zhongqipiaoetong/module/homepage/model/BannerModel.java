/**
 * banner模型
 */
package com.rd.zhongqipiaoetong.module.homepage.model;

import java.util.List;

/**
 * @author yoseflin
 *         banner模型
 */
public class BannerModel {
    public class Banner {
        /**
         *
         * 图片URL
         */
        private String imageUrl;
        /**
         * 链接URL
         */
        private String url;
        /**
         * 内容标题
         */
        private String title;

        private String content;
        //--------------------------华丽分割线----------------------------------

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

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
    }

    private String       qiniuDomain;
    private List<Banner> bannerList;

    public String getQiniuDomain() {
        return qiniuDomain;
    }

    public void setQiniuDomain(String qiniuDomain) {
        this.qiniuDomain = qiniuDomain;
    }

    public List<Banner> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<Banner> bannerList) {
        this.bannerList = bannerList;
    }
}