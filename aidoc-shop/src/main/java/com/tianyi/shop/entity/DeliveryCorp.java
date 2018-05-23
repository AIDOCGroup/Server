/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.shop.entity;

import com.tianyi.framework.entity.BaseEntity;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 实体类 - 物流公司
 *
 * @author Gray.Z
 * @date 2018/5/6 22:34.
 */
@Entity
@Table(name = "shop_DeliveryCorp")
public class DeliveryCorp extends BaseEntity<Long> {

    private static final long serialVersionUID = 10595703086045998L;
    /**
     * 名称
     */
    private String name;
    /**
     * 网址
     */
    private String url;
    /**
     * 排序
     */
    private Integer orderList;
    /**
     * 配送方式
     */
    private Set<DeliveryType> deliveryTypeSet;

    @Column(nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(nullable = false)
    public Integer getOrderList() {
        return orderList;
    }

    public void setOrderList(Integer orderList) {
        this.orderList = orderList;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "defaultDeliveryCorp")
    public Set<DeliveryType> getDeliveryTypeSet() {
        return deliveryTypeSet;
    }

    public void setDeliveryTypeSet(Set<DeliveryType> deliveryTypeSet) {
        this.deliveryTypeSet = deliveryTypeSet;
    }

}