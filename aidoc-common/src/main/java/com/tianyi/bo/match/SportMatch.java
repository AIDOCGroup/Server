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
import javax.persistence.Transient;

/**
 * server
 *
 * @author Gray.Z
 * @date 2018/5/7 11:44.
 */
@Entity
@Table(name = "sport_match", schema = "aidoc")
public class SportMatch extends BaseEntity<Long> {

    private String period;
    private Integer type;
    private String title;
    private String remark;
    private String coverImageUrl;
    private String ruleDesc;
    private BigDecimal totalReward;
    private Date startDate;
    private Date endDate;
    private Date enterStartDate;
    private Date enterEndDate;
    private BigDecimal enterCost;
    private Long enterCount;
    private Integer targetSteps;

    @Basic
    @Column(name = "period", nullable = true, length = 32)
    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    @Basic
    @Column(name = "type", nullable = true)
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Basic
    @Column(name = "title", nullable = true, length = 64)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "remark", nullable = true, length = 255)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "cover_image_url", nullable = true, length = 255)
    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    @Basic
    @Column(name = "rule_desc", nullable = true, length = -1)
    public String getRuleDesc() {
        return ruleDesc;
    }

    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc;
    }

    @Basic
    @Column(name = "total_reward", nullable = true, precision = 19, scale = 6)
    public BigDecimal getTotalReward() {
        return totalReward;
    }

    public void setTotalReward(BigDecimal totalReward) {
        this.totalReward = totalReward;
    }

    @Basic
    @Column(name = "start_date", nullable = false)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "end_date", nullable = false)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "enter_start_date", nullable = false)
    public Date getEnterStartDate() {
        return enterStartDate;
    }

    public void setEnterStartDate(Date enterStartDate) {
        this.enterStartDate = enterStartDate;
    }

    @Basic
    @Column(name = "enter_end_date", nullable = false)
    public Date getEnterEndDate() {
        return enterEndDate;
    }

    public void setEnterEndDate(Date enterEndDate) {
        this.enterEndDate = enterEndDate;
    }

    @Basic
    @Column(name = "enter_cost", nullable = true, precision = 19, scale = 6)
    public BigDecimal getEnterCost() {
        return enterCost;
    }

    public void setEnterCost(BigDecimal enterCost) {
        this.enterCost = enterCost;
    }

    @Basic
    @Column(name = "target_steps", nullable = false)
    public Integer getTargetSteps() {
        return targetSteps;
    }

    public void setTargetSteps(Integer targetSteps) {
        this.targetSteps = targetSteps;
    }

    @Transient
    public Long getEnterCount() {
        return enterCount;
    }

    public void setEnterCount(Long enterCount) {
        this.enterCount = enterCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SportMatch that = (SportMatch) o;

        if (id != that.id) {
            return false;
        }
        if (period != null ? !period.equals(that.period) : that.period != null) {
            return false;
        }
        if (type != null ? !type.equals(that.type) : that.type != null) {
            return false;
        }
        if (title != null ? !title.equals(that.title) : that.title != null) {
            return false;
        }
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) {
            return false;
        }
        if (coverImageUrl != null ? !coverImageUrl.equals(that.coverImageUrl) : that.coverImageUrl != null) {
            return false;
        }
        if (ruleDesc != null ? !ruleDesc.equals(that.ruleDesc) : that.ruleDesc != null) {
            return false;
        }
        if (totalReward != null ? !totalReward.equals(that.totalReward) : that.totalReward != null) {
            return false;
        }
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) {
            return false;
        }
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) {
            return false;
        }
        if (enterCost != null ? !enterCost.equals(that.enterCost) : that.enterCost != null) {
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
        result = 31 * result + (period != null ? period.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (coverImageUrl != null ? coverImageUrl.hashCode() : 0);
        result = 31 * result + (ruleDesc != null ? ruleDesc.hashCode() : 0);
        result = 31 * result + (totalReward != null ? totalReward.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (enterCost != null ? enterCost.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (modifyDate != null ? modifyDate.hashCode() : 0);
        return result;
    }
}
