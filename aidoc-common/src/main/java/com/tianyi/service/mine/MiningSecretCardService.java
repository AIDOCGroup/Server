/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.service.mine;

import com.tianyi.bo.MiningSecretCard;
import com.tianyi.framework.bean.Page;
import com.tianyi.framework.bean.Pageable;
import com.tianyi.framework.service.IGenericService;
import java.util.List;

/**
 * server
 *
 * @author Gray.Z
 * @date 2018/4/11 16:51.
 */
public interface MiningSecretCardService extends IGenericService<MiningSecretCard,Long> {

    MiningSecretCard getMiningSecretCard(Long id);

    List<MiningSecretCard> getUserMiningSecretCards(Long userId);

    List<MiningSecretCard> getAllActiveCards();

    Page<MiningSecretCard> findAll(Pageable pageable);

    void remove(Long id);

}
