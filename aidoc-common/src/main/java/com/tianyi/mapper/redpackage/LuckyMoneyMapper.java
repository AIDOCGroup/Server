package com.tianyi.mapper.redpackage;

import com.tianyi.vo.redpackage.LuckyMoney;
import com.tianyi.vo.redpackage.RedPackageListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface LuckyMoneyMapper {

    int deleteByPrimaryKey(Long id);

//    int insert(LuckyMoney record);

    int insert(LuckyMoney record);

    int insertSelective(LuckyMoney record);

    LuckyMoney selectByPrimaryKey(Long id);

    /**
     * 查询用户领取的红包
     * @param userId
     * @param
     * @return
     */

    List<LuckyMoney> selectByUserIdType(@Param("userId") Long userId);

    /**
     * 查询用户发出的红包
     * @param
     * @return
     */
    List<LuckyMoney> selectSendByUserIdType(@Param("userId") Long userId);

    int updateByPrimaryKeySelective(LuckyMoney record);

    int updateByPrimaryKey(LuckyMoney record);

    /**
     * 根据用户手机号和红包id查询是否领过红包
     */
    Long selectByMobileAndPrimaryId(@Param("packetId") Long packetId,@Param("phone") String phone);

    /**
     * 查询当日用户发出的红包额度
     */
    Long selectByUserIdAndType(@Param("userId") Long userId);

    /**
     * 查询发红包的用户信息
     */
    LuckyMoney selectByIdForSender(@Param("id") Long Id,@Param("mobile") String mobile);
    /**
     * 查询发红包的用户信息
     */
    LuckyMoney selectByIdForSenders(@Param("id") Long Id);

    /**
     * 查询发红包个数
     */
    RedPackageListVo selectPackageAmountByType(@Param("userId") Long userId, @Param("type")int type);

}