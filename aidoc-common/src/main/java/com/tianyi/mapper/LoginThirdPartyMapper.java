package com.tianyi.mapper;

import com.tianyi.dto.UserThirdPartyDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * author:CDH
 * Date:2018/5/7
 */
@Mapper
@Component
public interface LoginThirdPartyMapper {

    UserThirdPartyDto getLoginThirdParty(@Param("uid")String uid);

    void insertThirdParty(UserThirdPartyDto userThirdPartyDto);

    void updateThirdParty(UserThirdPartyDto userThirdPartyDto);
}
