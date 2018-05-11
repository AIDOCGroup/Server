/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.dao.mine;

import com.tianyi.bo.MiningSecretCard;
import com.tianyi.framework.dao.IGenericRepository;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 挖矿卡DAO
 *
 * @author Gray.Z
 * @date 2018/4/11 16:46.
 */
@Repository
public interface MiningSecretCardDao extends IGenericRepository<MiningSecretCard, Long> {

    List<MiningSecretCard> findByUserIdAndStatusAndInvalidDateGreaterThan(Long userId, int status,
        Date invalidDate);




}
