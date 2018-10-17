package com.tianyi.mapper;

import com.tianyi.vo.AppDividendsDetailUserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * server
 *
 * @author GaoZhilai
 * @date 2018.10.17.
 */
@Mapper
public interface ActivityDividendsDetailMapper {|
    int getInvitedNum(@Param("userId") Long userId);

    int haveIBeenInvited(@Param("userId") Long userId);

    List<Long> getMyInvitedUserId(@Param("userId") Long userId);

    List<AppDividendsDetailUserVO> getAppDividendsDetailUserList();
}
