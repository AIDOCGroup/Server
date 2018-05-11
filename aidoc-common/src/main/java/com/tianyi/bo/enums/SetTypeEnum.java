package com.tianyi.bo.enums;

/**
 * server
 *
 * @author GaoZhilai
 * @date 2018/4/14.
 */
public enum SetTypeEnum implements BaseEnum {
    /**
     * 此枚举类维护aidoc_set表中的设置类型
     */

    AidocTotal(1, "步数奖励设置"),
    ComputingPower(2, "算力比例"),
    Promotion(3, "活动奖励"),
    RateOfReturn(4, "收益率"),
    ESTIMATE_PERSONAL_AIDOC(5, "预计个人发币量"),
    DAILY_QUOTA(6,"每日提笔上限"),
    MONTHLY_QUOTA(7,"每月提笔上限");

    int code;
    String description;

    SetTypeEnum(int code, String description) {
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
