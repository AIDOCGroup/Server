/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.web.controller.vo;

import java.util.Date;

/**
 * 矿池算力
 *
 * @author Gray.Z
 * @date 2018/4/24 13:12.
 */
public class MineComputeVO extends BaseVO {

    private String type;
    private Date date;


    public MineComputeVO(String name, String value) {
        super(name, value);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
