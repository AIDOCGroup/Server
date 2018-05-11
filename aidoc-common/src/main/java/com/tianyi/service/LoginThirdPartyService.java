package com.tianyi.service;

import com.tianyi.dto.UserThirdPartyDto;

/**
 * author:CDH
 * Date:2018/5/7
 */
public interface LoginThirdPartyService {
    /**
     * 查询第三方信息
     * @param uid
     * @return UserThirdPartyVo
     */
    UserThirdPartyDto getLoginThirdParty(String uid);

    void insertThirdParty(UserThirdPartyDto userThirdPartyDto);

    void updateThirdParty(UserThirdPartyDto userThirdPartyDto);
}
