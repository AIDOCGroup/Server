package com.tianyi.bo.enums;

/**
 * author:CDH
 * Date:2018/5/22
 */
public enum TransferEnum implements BaseEnum{

    PROCESSING(1, "处理中"),
    BONANZA(2, "成功"),
    FAILURE(3, "失败"),
    REFUND(4, "退款");

    int code;
    String description;

    TransferEnum(int code, String description) {
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
