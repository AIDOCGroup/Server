/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.bo.mine;

import com.tianyi.framework.entity.BaseEntity;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 算力规则配置
 *
 * @author Gray.Z
 * @date 2018/4/21 13:16.
 */
@Entity
@Table(name = "compute_rule_config", schema = "aidoc")
public class ComputeRuleConfig extends BaseEntity<Long> {

    public enum ComputeValidType {
        TEMP, FOREVER
    }

    public enum FrequencyType {
        ONCE_OF_DAY, ONCE_OF_3DAY, ONCE_OF_WEEK, ONCE_OF_YEAR, HUNDRED_OF_DAY
    }


    private Integer type;
    private String remark;
    private BigDecimal unitComputeValue;
    private Integer computeValidDay;
    private Integer computeValueUpper;
    private Integer computeValidType;
    private Integer frequencyType;
    private Integer profitValidInterval;
    private Integer profitInvalidInterval;


    @Basic
    @Column(name = "type", nullable = true)
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
    @Column(name = "unit_compute_value", nullable = true, precision = 19,scale = 4)
    public BigDecimal getUnitComputeValue() {
        return unitComputeValue;
    }

    public void setUnitComputeValue(BigDecimal unitComputeValue) {
        this.unitComputeValue = unitComputeValue;
    }

    @Basic
    @Column(name = "compute_valid_day", nullable = true)
    public Integer getComputeValidDay() {
        return computeValidDay;
    }

    public void setComputeValidDay(Integer computeValidDay) {
        this.computeValidDay = computeValidDay;
    }

    @Basic
    @Column(name = "compute_value_upper", nullable = true)
    public Integer getComputeValueUpper() {
        return computeValueUpper;
    }

    public void setComputeValueUpper(Integer computeValueUpper) {
        this.computeValueUpper = computeValueUpper;
    }

    @Basic
    @Column(name = "compute_valid_type", nullable = true)
    public Integer getComputeValidType() {
        return computeValidType;
    }

    public void setComputeValidType(Integer computeValidType) {
        this.computeValidType = computeValidType;
    }

    @Basic
    @Column(name = "frequency_type", nullable = true)
    public Integer getFrequencyType() {
        return frequencyType;
    }

    public void setFrequencyType(Integer frequencyType) {
        this.frequencyType = frequencyType;
    }

    @Basic
    @Column(name = "profit_valid_interval", nullable = true)
    public Integer getProfitValidInterval() {
        return profitValidInterval;
    }

    public void setProfitValidInterval(Integer profitValidInterval) {
        this.profitValidInterval = profitValidInterval;
    }

    @Basic
    @Column(name = "profit_invalid_interval", nullable = true)
    public Integer getProfitInvalidInterval() {
        return profitInvalidInterval;
    }

    public void setProfitInvalidInterval(Integer profitInvalidInterval) {
        this.profitInvalidInterval = profitInvalidInterval;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ComputeRuleConfig that = (ComputeRuleConfig) o;

        if (id != that.id) {
            return false;
        }
        if (type != null ? !type.equals(that.type) : that.type != null) {
            return false;
        }
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) {
            return false;
        }
        if (unitComputeValue != null ? !unitComputeValue.equals(that.unitComputeValue)
            : that.unitComputeValue != null) {
            return false;
        }
        if (computeValidDay != null ? !computeValidDay.equals(that.computeValidDay)
            : that.computeValidDay != null) {
            return false;
        }
        if (computeValueUpper != null ? !computeValueUpper.equals(that.computeValueUpper)
            : that.computeValueUpper != null) {
            return false;
        }
        if (computeValidType != null ? !computeValidType.equals(that.computeValidType)
            : that.computeValidType != null) {
            return false;
        }
        if (frequencyType != null ? !frequencyType.equals(that.frequencyType)
            : that.frequencyType != null) {
            return false;
        }
        if (profitValidInterval != null ? !profitValidInterval.equals(that.profitValidInterval)
            : that.profitValidInterval != null) {
            return false;
        }
        if (profitInvalidInterval != null ? !profitInvalidInterval
            .equals(that.profitInvalidInterval)
            : that.profitInvalidInterval != null) {
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
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (unitComputeValue != null ? unitComputeValue.hashCode() : 0);
        result = 31 * result + (computeValidDay != null ? computeValidDay.hashCode() : 0);
        result = 31 * result + (computeValueUpper != null ? computeValueUpper.hashCode() : 0);
        result = 31 * result + (computeValidType != null ? computeValidType.hashCode() : 0);
        result = 31 * result + (frequencyType != null ? frequencyType.hashCode() : 0);
        result = 31 * result + (profitValidInterval != null ? profitValidInterval.hashCode() : 0);
        result =
            31 * result + (profitInvalidInterval != null ? profitInvalidInterval.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (modifyDate != null ? modifyDate.hashCode() : 0);
        return result;
    }
}
