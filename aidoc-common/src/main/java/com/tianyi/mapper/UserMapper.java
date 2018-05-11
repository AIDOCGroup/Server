package com.tianyi.mapper;

import com.tianyi.bo.User;
import com.tianyi.vo.ThirdPartyVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * server
 *
 * @author GaoZhilai
 * @date 2018/4/10.
 */
@Mapper
@Component
public interface UserMapper {

    public abstract User getUserById(Long userId);

    User getOpenIdUser(@Param("userId")long userId);

    ThirdPartyVo getUserPhone(@Param("phone")String phone);
}
