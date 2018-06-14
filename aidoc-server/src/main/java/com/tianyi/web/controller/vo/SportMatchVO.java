/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.web.controller.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import java.util.Date;

/**
 * server
 *
 * @author Gray.Z
 * @date 2018/5/7 17:20.
 */
@JsonInclude(Include.NON_NULL)
public class SportMatchVO {

    private Long id;
    private String period;
    private Integer type;
    private String title;
    private String remark;
    private String coverImageUrl;
    private String ruleDesc;
    private Date startDate;
    private Date endDate;
    private Date enterStartDate;
    private Date enterEndDate;
    private BigDecimal enterCost;
    private Long enterCount;
    private BigDecimal totalReward;
    private Integer targetSteps;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getRuleDesc() {
        return ruleDesc;
    }

    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getEnterCost() {
        return enterCost;
    }

    public void setEnterCost(BigDecimal enterCost) {
        this.enterCost = enterCost;
    }

    public Long getEnterCount() {
        return enterCount;
    }

    public void setEnterCount(Long enterCount) {
        this.enterCount = enterCount;
    }

    public BigDecimal getTotalReward() {
        return totalReward;
    }

    public void setTotalReward(BigDecimal totalReward) {
        this.totalReward = totalReward;
    }

    public Integer getTargetSteps() {
        return targetSteps;
    }

    public void setTargetSteps(Integer targetSteps) {
        this.targetSteps = targetSteps;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getEnterStartDate() {
        return enterStartDate;
    }

    public void setEnterStartDate(Date enterStartDate) {
        this.enterStartDate = enterStartDate;
    }

    public Date getEnterEndDate() {
        return enterEndDate;
    }

    public void setEnterEndDate(Date enterEndDate) {
        this.enterEndDate = enterEndDate;
    }
}
