/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.bo.match;

import com.tianyi.framework.entity.BaseEntity;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * server
 *
 * @author Gray.Z
 * @date 2018/5/9 11:09.
 */
@Entity
@Table(name = "user_match_steps", schema = "aidoc")
public class UserMatchSteps extends BaseEntity<Long> {

    private Long userId;
    private Long matchId;
    private String period;
    private Integer steps;
    private BigDecimal rewards;
    private Date matchDate;
    private Boolean isAddition;

    @Basic
    @Column(name = "user_id", nullable = true)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "match_id", nullable = true)
    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    @Basic
    @Column(name = "period", nullable = true, length = 32)
    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    @Basic
    @Column(name = "steps", nullable = true)
    public Integer getSteps() {
        return steps;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }

    @Basic
    @Column(name = "rewards", nullable = true, precision = 19, scale = 6)
    public BigDecimal getRewards() {
        return rewards;
    }

    public void setRewards(BigDecimal rewards) {
        this.rewards = rewards;
    }

    @Basic
    @Column(name = "match_date", nullable = true)
    public Date getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }

    @Basic
    @Column(name = "is_addition", nullable = true)
    public Boolean getAddition() {
        return isAddition;
    }

    public void setAddition(Boolean addition) {
        isAddition = addition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserMatchSteps that = (UserMatchSteps) o;

        if (id != that.id) {
            return false;
        }
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) {
            return false;
        }
        if (period != null ? !period.equals(that.period) : that.period != null) {
            return false;
        }
        if (steps != null ? !steps.equals(that.steps) : that.steps != null) {
            return false;
        }
        if (rewards != null ? !rewards.equals(that.rewards) : that.rewards != null) {
            return false;
        }
        if (matchDate != null ? !matchDate.equals(that.matchDate) : that.matchDate != null) {
            return false;
        }
        if (isAddition != null ? !isAddition.equals(that.isAddition) : that.isAddition != null) {
            return false;
        }
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) {
            return false;
        }
        if (modifyDate != null ? !modifyDate.equals(that.modifyDate) : that.modifyDate != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (period != null ? period.hashCode() : 0);
        result = 31 * result + (steps != null ? steps.hashCode() : 0);
        result = 31 * result + (rewards != null ? rewards.hashCode() : 0);
        result = 31 * result + (matchDate != null ? matchDate.hashCode() : 0);
        result = 31 * result + (isAddition != null ? isAddition.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (modifyDate != null ? modifyDate.hashCode() : 0);
        return result;
    }
}
