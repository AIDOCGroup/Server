package com.tianyi.bo.enums;

/**
 * Created by gaozhilai on 2018/4/3.
 */
public enum StatusEnum implements BaseEnum{
    Available(1,"启用"),
    Forbidden(0,"禁用");
    int code;
    String description;

    StatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }
    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
