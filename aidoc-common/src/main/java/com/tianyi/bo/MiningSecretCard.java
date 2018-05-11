/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.bo;

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
 * @date 2018/4/11 16:40.
 */
@Entity
@Table(name = "mining_secret_card", schema = "aidoc")
public class MiningSecretCard extends BaseEntity<Long> {


    public enum SecretCardStatus {
        NORMAL, ACTIVE, INVALID, DELETED, DISABLE
    }

    private String serialNo;
    private String orderNo;
    private Integer validDay;
    private BigDecimal price;
    private Double discount;
    private Long userId;
    private String username;
    private Integer buyChannel;
    private Integer status;
    private Date activeDate;
    private Date invalidDate;
    private BigDecimal leftTime;

    @Basic
    @Column(name = "serial_no", nullable = true, length = 32, unique = true)
    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    @Basic
    @Column(name = "order_no", nullable = true, length = 64)
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Basic
    @Column(name = "valid_day", nullable = true)
    public Integer getValidDay() {
        return validDay;
    }

    public void setValidDay(Integer validDay) {
        this.validDay = validDay;
    }

    @Basic
    @Column(name = "price", nullable = true, precision = 19, scale = 2)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Basic
    @Column(name = "discount", nullable = true, precision = 2, scale = 2)
    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    @Basic
    @Column(name = "user_id", nullable = true)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "username", nullable = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "buy_channel", nullable = true)
    public Integer getBuyChannel() {
        return buyChannel;
    }

    public void setBuyChannel(Integer buyChannel) {
        this.buyChannel = buyChannel;
    }

    @Basic
    @Column(name = "status", nullable = true)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "active_date", nullable = true)
    public Date getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
    }

    @Basic
    @Column(name = "invalid_date", nullable = true)
    public Date getInvalidDate() {
        return invalidDate;
    }

    public void setInvalidDate(Date invalidDate) {
        this.invalidDate = invalidDate;
    }

    @Transient
    public BigDecimal getLeftTime() {
        return leftTime;
    }

    public void setLeftTime(BigDecimal leftTime) {
        this.leftTime = leftTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MiningSecretCard that = (MiningSecretCard) o;

        if (id != that.id) {
            return false;
        }
        if (serialNo != null ? !serialNo.equals(that.serialNo) : that.serialNo != null) {
            return false;
        }
        if (validDay != null ? !validDay.equals(that.validDay) : that.validDay != null) {
            return false;
        }
        if (price != null ? !price.equals(that.price) : that.price != null) {
            return false;
        }
        if (discount != null ? !discount.equals(that.discount) : that.discount != null) {
            return false;
        }
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) {
            return false;
        }
        if (status != null ? !status.equals(that.status) : that.status != null) {
            return false;
        }
        if (activeDate != null ? !activeDate.equals(that.activeDate) : that.activeDate != null) {
            return false;
        }
        if (invalidDate != null ? !invalidDate.equals(that.invalidDate)
            : that.invalidDate != null) {
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
        result = 31 * result + (serialNo != null ? serialNo.hashCode() : 0);
        result = 31 * result + (validDay != null ? validDay.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (discount != null ? discount.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (activeDate != null ? activeDate.hashCode() : 0);
        result = 31 * result + (invalidDate != null ? invalidDate.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (modifyDate != null ? modifyDate.hashCode() : 0);
        return result;
    }
}
