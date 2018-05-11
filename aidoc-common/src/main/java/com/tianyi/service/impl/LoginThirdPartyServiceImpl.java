package com.tianyi.service.impl;

import com.tianyi.dto.UserThirdPartyDto;
import com.tianyi.mapper.LoginThirdPartyMapper;
import com.tianyi.service.LoginThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author:CDH
 * Date:2018/5/7
 */
@Service
public class LoginThirdPartyServiceImpl implements LoginThirdPartyService {

    @Autowired
    LoginThirdPartyMapper loginThirdPartyMapper;

    @Override
    public void updateThirdParty(UserThirdPartyDto userThirdPartyDto) {
        loginThirdPartyMapper.updateThirdParty(userThirdPartyDto);
    }

    @Override
    public void insertThirdParty(UserThirdPartyDto userThirdPartyDto) {
        loginThirdPartyMapper.insertThirdParty(userThirdPartyDto);
    }

    @Override
    public UserThirdPartyDto getLoginThirdParty(String uid) {
        UserThirdPartyDto userThirdPartyDto = loginThirdPartyMapper.getLoginThirdParty(uid);
        return userThirdPartyDto;
    }
}
