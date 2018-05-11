package com.tianyi.bo.enums;

/**
 * Created by gaozhilai on 2018/3/27.
 */
public enum ChannelEnum implements BaseEnum{
    WeChat(1,"微信"),
    MicroBlog(2,"微博"),
    Telegraph(3,"电报群"),
    Beta(4,"内测群"),
    Client(5,"客户端");

    int code;
    String description;

    ChannelEnum(int code, String description) {
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
