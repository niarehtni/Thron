package com.rd.zhongqipiaoetong.module.account.model;

import com.rd.zhongqipiaoetong.network.entity.ListMo;

/**
 * Created by Administrator on 2016/9/9.
 */
public class ScoreLogListMo {
    private ListMo<ScoreLogMo> scoreLogList;
    private String             validScore;

    public ListMo<ScoreLogMo> getScoreLogList() {
        return scoreLogList;
    }

    public void setScoreLogList(ListMo<ScoreLogMo> scoreLogList) {
        this.scoreLogList = scoreLogList;
    }

    public String getValidScore() {
        return validScore;
    }

    public void setValidScore(String validScore) {
        this.validScore = validScore;
    }
}
