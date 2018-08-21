package com.rd.zhongqipiaoetong.module.more.model;

import android.text.Html;
import android.text.Spanned;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2016/2/18 14:48
 * <p/>
 * Description:
 */
public class MsgMo {
        /**
         * 消息id
         */
        private String  id;
        /**
         * 消息标题
         */
        private String  title;
        /**
         * 消息内容
         */
        private String  content;
        /**
         * 添加时间
         */
        private String  addTime;
        /**
         * 1-未读,2已读
         */
        private String isReaded;
        ///////////////////////////////////////////////////////////////////////////
        // 业务逻辑处理属性
        ///////////////////////////////////////////////////////////////////////////
        /**
         * 是否选中
         */
        private boolean isCheck;
        /**
         * 是否显示详情
         */
        private boolean isShow;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Spanned getContent() {
            return Html.fromHtml(content);
        }

        public void setContent(String content) {
            this.content = content;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setIsCheck(boolean isCheck) {
            this.isCheck = isCheck;
        }

        public boolean isShow() {
            return isShow;
        }

        public void setIsShow(boolean isShow) {
            this.isShow = isShow;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIsReaded() {
            return isReaded;
        }

        public void setIsReaded(String isReaded) {
            this.isReaded = isReaded;
        }

    public MsgMo() {
    }


}