/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.framework.dao;

import com.tianyi.framework.entity.BaseEntity;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 实体类-基类
 *
 * @author Gray.Z
 * @date 2018/4/3 20:25.
 */
@NoRepositoryBean
public interface IGenericRepository<T extends BaseEntity<ID>, ID extends Serializable> extends JpaRepository<T, ID>,
    JpaSpecificationExecutor<T> {

}
