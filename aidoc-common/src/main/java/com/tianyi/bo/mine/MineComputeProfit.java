/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.bo.mine;

import com.tianyi.framework.entity.BaseEntity;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 矿池算力收益
 *
 * @author Gray.Z
 * @date 2018/4/21 13:16.
 */
@Entity
@Table(name = "mine_compute_profit", schema = "aidoc")
public class MineComputeProfit extends BaseEntity<Long> {

    private Long userId;
    private Integer computeType;
    private String businessId;
    private String remark;
    private Integer computeValue;
    private Date computeInvalidDate;
    private Boolean isComputeEnable;
    private BigDecimal computeProfit;
    private Boolean isProfitCollect;
    private Date profitCollectDate;
    private Date profitValidDate;
    private Date profitInvalidDate;

    @Basic
    @Column(name = "user_id", nullable = true)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "compute_type", nullable = true)
    public Integer getComputeType() {
        return computeType;
    }

    public void setComputeType(Integer computeType) {
        this.computeType = computeType;
    }

    @Basic
    @Column(name = "business_id", nullable = true)
    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
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
    @Column(name = "compute_value", nullable = true)
    public Integer getComputeValue() {
        return computeValue;
    }

    public void setComputeValue(Integer computeValue) {
        this.computeValue = computeValue;
    }

    @Basic
    @Column(name = "compute_invalid_date", nullable = true)
    public Date getComputeInvalidDate() {
        return computeInvalidDate;
    }

    public void setComputeInvalidDate(Date computeInvalidDate) {
        this.computeInvalidDate = computeInvalidDate;
    }

    @Basic
    @Column(name = "is_compute_enable", nullable = true)
    public Boolean getComputeEnable() {
        return isComputeEnable;
    }

    public void setComputeEnable(Boolean computeEnable) {
        isComputeEnable = computeEnable;
    }

    @Basic
    @Column(name = "compute_profit", nullable = true, precision = 19, scale = 4)
    public BigDecimal getComputeProfit() {
        return computeProfit;
    }

    public void setComputeProfit(BigDecimal computeProfit) {
        this.computeProfit = computeProfit;
    }

    @Basic
    @Column(name = "is_profit_collect", nullable = true)
    public Boolean getProfitCollect() {
        return isProfitCollect;
    }

    public void setProfitCollect(Boolean profitCollect) {
        isProfitCollect = profitCollect;
    }

    @Basic
    @Column(name = "profit_collect_date", nullable = true)
    public Date getProfitCollectDate() {
        return profitCollectDate;
    }

    public void setProfitCollectDate(Date profitCollectDate) {
        this.profitCollectDate = profitCollectDate;
    }

    @Basic
    @Column(name = "profit_valid_date", nullable = true)
    public Date getProfitValidDate() {
        return profitValidDate;
    }

    public void setProfitValidDate(Date profitValidDate) {
        this.profitValidDate = profitValidDate;
    }

    @Basic
    @Column(name = "profit_invalid_date", nullable = true)
    public Date getProfitInvalidDate() {
        return profitInvalidDate;
    }

    public void setProfitInvalidDate(Date profitInvalidDate) {
        this.profitInvalidDate = profitInvalidDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MineComputeProfit that = (MineComputeProfit) o;

        if (id != that.id) {
            return false;
        }
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) {
            return false;
        }
        if (computeType != null ? !computeType.equals(that.computeType)
            : that.computeType != null) {
            return false;
        }
        if (businessId != null ? !businessId.equals(that.businessId) : that.businessId != null) {
            return false;
        }
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) {
            return false;
        }
        if (computeValue != null ? !computeValue.equals(that.computeValue)
            : that.computeValue != null) {
            return false;
        }
        if (computeInvalidDate != null ? !computeInvalidDate.equals(that.computeInvalidDate)
            : that.computeInvalidDate != null) {
            return false;
        }
        if (isComputeEnable != null ? !isComputeEnable.equals(that.isComputeEnable)
            : that.isComputeEnable != null) {
            return false;
        }
        if (computeProfit != null ? !computeProfit.equals(that.computeProfit)
            : that.computeProfit != null) {
            return false;
        }
        if (isProfitCollect != null ? !isProfitCollect.equals(that.isProfitCollect)
            : that.isProfitCollect != null) {
            return false;
        }
        if (profitValidDate != null ? !profitValidDate.equals(that.profitValidDate)
            : that.profitValidDate != null) {
            return false;
        }
        if (profitInvalidDate != null ? !profitInvalidDate.equals(that.profitInvalidDate)
            : that.profitInvalidDate != null) {
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
        result = 31 * result + (computeType != null ? computeType.hashCode() : 0);
        result = 31 * result + (businessId != null ? businessId.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (computeValue != null ? computeValue.hashCode() : 0);
        result = 31 * result + (computeInvalidDate != null ? computeInvalidDate.hashCode() : 0);
        result = 31 * result + (isComputeEnable != null ? isComputeEnable.hashCode() : 0);
        result = 31 * result + (computeProfit != null ? computeProfit.hashCode() : 0);
        result = 31 * result + (isProfitCollect != null ? isProfitCollect.hashCode() : 0);
        result = 31 * result + (profitValidDate != null ? profitValidDate.hashCode() : 0);
        result = 31 * result + (profitInvalidDate != null ? profitInvalidDate.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (modifyDate != null ? modifyDate.hashCode() : 0);
        return result;
    }
}
