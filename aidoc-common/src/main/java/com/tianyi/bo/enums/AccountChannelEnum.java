package com.tianyi.bo.enums;

import com.google.common.collect.Lists;
import com.tianyi.framework.util.Localize;

/**
 * server
 *
 * @author GaoZhilai
 * @date 2018/4/17.
 */
public enum AccountChannelEnum implements BaseEnum {

    UNKNOWN(-1, "wallet_channel_unknow"),
    GIVE(1, "wallet_channel_give"),
    INVITATION1(2, "wallet_channel_invitation1"),
    INVIREGIST(3, "wallet_channel_inviregist"),

    TRANSFER(4, "wallet_channel_transfer"),
    MINING_CARD(5, "wallet_channel_mining_card"),

    BIND_DEVICE(6, "wallet_channel_bind_device"),
    BIND_BRACELET(7, "wallet_channel_bind_bracelet"),
    BIND_WATCH(8, "wallet_channel_bind_watch"),
    BIND_BLOOD_PRESSURE_METER(9, "wallet_channel_bind_blood_pressure_meter"),
    BIND_BLOOD_GLUCOSE_METER(10, "wallet_channel_bind_blood_glucose_meter"),
    BIND_THERMOMETER(11, "wallet_channel_bind_thermometer"),
    BIND_WEIGHING_SCALE(12, "wallet_channel_bind_weighing_scale"),

    MEASURE_WEIGHT(13, "wallet_channel_measure_weight"),
    MEASURE_TEMPERATURE(14, "wallet_channel_measure_temperature"),
    MEASURE_BLOOD_PRESSURE(15, "wallet_channel_measure_blood_pressure"),
    MEASURE_BLOOD_SUGAR(16, "wallet_channel_measure_blood_sugar"),
    MEASURE_HEART_RATE(17, "wallet_channel_measure_heart_rate"),
    SLEEP_QUALITY_CHECK(18, "wallet_channel_sleep_quality_check"),
    SKIN_AI_CHECK(19, "wallet_channel_skin_ai_check"),
    USER_LOGON(20, "wallet_channel_user_logon"),

    SPORT_MATCH_ENTER(21,"wallet_channel_match_enter_cost"),
    SPORT_MATCH_REWARD(22,"wallet_channel_match_reward");

    private int code;
    private String description;

    AccountChannelEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static AccountChannelEnum getByName(String name) {
        return Lists.newArrayList(AccountChannelEnum.values()).stream().filter(channel -> channel.name().equals(name)).findFirst()
            .orElse(AccountChannelEnum.UNKNOWN);
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return Localize.getMessage(description);
    }
}
