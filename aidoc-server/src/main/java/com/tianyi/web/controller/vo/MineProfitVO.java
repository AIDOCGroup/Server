/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.web.controller.vo;

/**
 * 矿池收益
 *
 * @author Gray.Z
 * @date 2018/4/23 10:29.
 */
public class MineProfitVO extends BaseVO {

    protected String id;
    /**
     * 剩余时间-分钟
     */
    private Integer leftTime;


    public MineProfitVO() {
    }

    public MineProfitVO(String name, String value) {
        super(name, value);
    }

    public MineProfitVO(String id, String name, String value) {
        super(name, value);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getLeftTime() {
        return leftTime;
    }

    public void setLeftTime(Integer leftTime) {
        this.leftTime = leftTime;
    }
}
