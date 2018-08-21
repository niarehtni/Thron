package com.rd.zhongqipiaoetong.network;

import com.rd.zhongqipiaoetong.utils.UrlUtils;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 6/2/16.
 */
public class Html5Util {
    /**
     * 帮助中心
     */
    public static final String MORE_HELP        = UrlUtils.getAddress() + "/app/extra/questions.html";
    /**
     * 公告
     */
    public static final String MORE_NOTICE      = UrlUtils.getAddress() + "/app/extra/notices.html";
    /**
     * 注册协议
     */
    public static final String SIGNUP_XY        = UrlUtils.getAddress() + "/app/extra/legal.html";
    /**
     * 生利宝
     */
    public static final String SHENGLIBAO       = "invest/interestGeneratedWealth.html";
    /**
     * 积分商城
     */
    public static final String SCOREMALL        = UrlUtils.getAddress() +"poinsMall/getPoinsMallPage";
    /**
     * 我的任务
     */
    public static final String MYTASK           = UrlUtils.getAddress() +"account/getMyTaskPage";
    /**
     * 风险评测
     */
    public static final String RISK             = UrlUtils.getAddress() + "/app/assUser/getAssQuestionPage";
    /**
     * 常见问题
     */
    public static final String MORE_HELPCONTENT = "/app/more/getOne";
    /**
     * 自动投标
     */
    public static final String API_AUTOINVEST = "account/borrowAutoTender/getBorrowAutoTenderInitPage";
    public static final String NOTICE_H5        = "/app/column/articleListPage";
    /**
     * 还款
     */
    public static final String REPAY            = UrlUtils.getAddress() + "/app/account/repaymentForm";
}

