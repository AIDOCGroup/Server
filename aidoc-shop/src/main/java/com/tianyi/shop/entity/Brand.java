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
 * 实体类 - 品牌
 *
 * @author Gray.Z
 * @date 2018/5/6 22:34.
 */
@Entity
@Table(name = "SHOP_BRAND")
public class Brand extends BaseEntity<Long> {

    private static final long serialVersionUID = -6109590619136943215L;

    /**
     * 名称
     */
    private String name;
    /**
     * Logo
     */
    private String logo;
    /**
     * 网址
     */
    private String url;
    /**
     * 介绍
     */
    private String introduction;
    /**
     * 排序
     */
    private Integer orderList;
    /**
     * 商品
     */
    private Set<Product> productSet;

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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

    @Column(length = 10000)
    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "brand")
    public Set<Product> getProductSet() {
        return productSet;
    }

    public void setProductSet(Set<Product> productSet) {
        this.productSet = productSet;
    }

}