/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.web.controller.vo;

import java.io.Serializable;

/**
 * server
 *
 * @author Gray.Z
 * @date 2018/4/22 21:18.
 */
public class BaseVO implements Serializable {


    protected String name;
    protected String value;

    public BaseVO() {
    }

    public BaseVO(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
