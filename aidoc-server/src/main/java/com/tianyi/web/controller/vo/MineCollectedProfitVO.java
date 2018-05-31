/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.web.controller.vo;

import java.util.Date;

/**
 * 矿池已收取收益
 *
 * @author Gray.Z
 * @date 2018/4/23 12:00.
 */
public class MineCollectedProfitVO extends BaseVO {

    /**
     * 已收取收益的生成时间 yyyy-MM-dd
     */
    private Date collectedDate;

    private Date createTime;

    public MineCollectedProfitVO(String name, String value) {
        super(name, value);
    }

    public Date getCollectedDate() {
        return collectedDate;
    }

    public void setCollectedDate(Date collectedDate) {
        this.collectedDate = collectedDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
