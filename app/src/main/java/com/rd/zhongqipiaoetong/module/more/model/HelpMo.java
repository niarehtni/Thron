package com.rd.zhongqipiaoetong.module.more.model;

import com.rd.zhongqipiaoetong.network.entity.PageMo;

import java.util.List;

/**
 * Created by Administrator on 2017/4/24.
 */
public class HelpMo extends PageMo {

    private List<HelpItem> helpCenterList;

    public List<HelpItem> getHelpCenterList() {
        return helpCenterList;
    }

    public void setHelpCenterList(List<HelpItem> helpCenterList) {
        this.helpCenterList = helpCenterList;
    }

    public class HelpItem{
        private String id;
        private String title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
