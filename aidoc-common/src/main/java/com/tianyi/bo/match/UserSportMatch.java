/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.bo.match;

import com.tianyi.framework.entity.BaseEntity;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * server
 *
 * @author Gray.Z
 * @date 2018/5/7 11:44.
 */
@Entity
@Table(name = "user_sport_match", schema = "aidoc")
public class UserSportMatch extends BaseEntity<Long> {

    private Long userId;
    private String username;
    private Integer enterStatus;
    private Integer matchResult;
    private BigDecimal matchReward;
    private SportMatch sportMatch;

    private Long matchId;
    private Integer matchType;

    @Basic
    @Column(name = "user_id", nullable = true)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Transient
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "enter_status", nullable = true)
    public Integer getEnterStatus() {
        return enterStatus;
    }

    public void setEnterStatus(Integer enterStatus) {
        this.enterStatus = enterStatus;
    }

    @Basic
    @Column(name = "match_result", nullable = true)
    public Integer getMatchResult() {
        return matchResult;
    }

    public void setMatchResult(Integer matchResult) {
        this.matchResult = matchResult;
    }

    @Basic
    @Column(name = "match_reward", nullable = true, precision = 19, scale = 6)
    public BigDecimal getMatchReward() {
        return matchReward;
    }

    public void setMatchReward(BigDecimal matchReward) {
        this.matchReward = matchReward;
    }

    @Transient
    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    @Transient
    public Integer getMatchType() {
        return matchType;
    }

    public void setMatchType(Integer matchType) {
        this.matchType = matchType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserSportMatch that = (UserSportMatch) o;

        if (id != that.id) {
            return false;
        }
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) {
            return false;
        }
        if (enterStatus != null ? !enterStatus.equals(that.enterStatus) : that.enterStatus != null) {
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
        result = 31 * result + (enterStatus != null ? enterStatus.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (modifyDate != null ? modifyDate.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "match_id", referencedColumnName = "ID")
    public SportMatch getSportMatch() {
        return sportMatch;
    }

    public void setSportMatch(SportMatch sportMatch) {
        this.sportMatch = sportMatch;
    }
}
