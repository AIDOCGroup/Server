/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.bo.enums;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.tianyi.framework.util.Localize;
import java.util.List;

/**
 * server
 *
 * @author Gray.Z
 * @date 2018/4/19 16:26.
 */
public enum ComputeItemType {
    SPORT_STEPS(1, "compute_item_sport_steps"),

    MEASURE_WEIGHT(2, "compute_item_measure_weight"),
    MEASURE_TEMPERATURE(3, "compute_item_measure_temperature"),
    MEASURE_BLOOD_PRESSURE(5, "compute_item_measure_blood_pressure"),
    MEASURE_BLOOD_SUGAR(6, "compute_item_measure_blood_sugar"),
    MEASURE_HEART_RATE(7, "compute_item_measure_heart_rate"),

    BIND_DEVICE(8, "compute_item_bind_device"),
    INFORMATION_SHARE(9, "compute_item_information_share"),
    INVITE_FRIENDS(10, "compute_item_invite_friends"),
    USER_LOGON(11, "compute_item_user_logon"),

    SLEEP_QUALITY_CHECK(12, "compute_item_sleep_quality_check"),
    SKIN_AI_CHECK(13, "compute_item_check"),
    UPDATE_USER_PROFILE(14, "compute_item_update_user_profile"),
    ;

    private int code;
    private String description;

    ComputeItemType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 目前可用的算力项集合
     */
    public static final List<ComputeItemType> ENABLE_COMPUTE_LIST = ImmutableList
        .of(SPORT_STEPS, MEASURE_WEIGHT, MEASURE_TEMPERATURE, MEASURE_BLOOD_SUGAR, BIND_DEVICE,
            INVITE_FRIENDS, USER_LOGON);

    public static ComputeItemType getByCode(int code) {
        return Lists.newArrayList(ComputeItemType.values()).stream()
            .filter(computeItemType -> computeItemType.getCode() == code).findFirst().get();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return Localize.getMessage(description);
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
