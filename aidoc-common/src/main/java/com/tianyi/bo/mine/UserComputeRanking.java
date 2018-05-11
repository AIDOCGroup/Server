/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.bo.mine;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.stereotype.Component;

/**
 * 用户算力排名DO
 *
 * @author Gray.Z
 * @date 2018/4/24 14:19.
 */
@Component
public class UserComputeRanking {

    @JsonIgnore
    private Long userId;
    private Integer computeValue;
    private Integer ranking;

    public UserComputeRanking() {
    }

    public UserComputeRanking(Long userId, Integer computeValue, Integer ranking) {
        this.userId = userId;
        this.computeValue = computeValue;
        this.ranking = ranking;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getComputeValue() {
        return computeValue;
    }

    public void setComputeValue(Integer computeValue) {
        this.computeValue = computeValue;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }
}
