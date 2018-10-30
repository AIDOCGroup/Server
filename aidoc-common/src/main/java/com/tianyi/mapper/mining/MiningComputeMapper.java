package com.tianyi.mapper.mining;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * server
 *
 * @author GaoZhilai
 * @date 2018/10/9.
 */
@Mapper
public interface MiningComputeMapper {
    int insertMiningCompute(MiningCompute miningCompute);

    int insertBatchMiningCompute(List<MiningCompute> miningComputes);

    double getTotalMiningComputeByUserId(@Param("userId") Long userId);

    List<MiningCompute> getMiningComputeHistoryByUserId(@Param("userId") Long userId);

    List<MiningComputeVO> getMiningComputeHistoryList(@Param("userId") Long userId);

    int invalidateMiningCompute(@Param("deviceUuid") String deviceUuid);

    int doesTheUserNeedGiveCompute(@Param("deviceUuid") String deviceUuid);

    BigDecimal getUserComputeLevelPercent(@Param("userId") Long userId);

    BigDecimal getBraceletUserComputeLevelPercent(@Param("userId") Long userId);

    double getUserComputeLevel(@Param("userId") Long userId);

    BigDecimal getCurrentTotalDeliveryAidoc(@Param("expectedIncreaseRegist") double expectedIncreaseRegist,
                                            @Param("goalRegist") double goalRegist,
                                            @Param("amountGiveAidocOfYear") double amountGiveAidocOfYear,
                                            @Param("xCoefficient") BigDecimal xCoefficient);

    List<Long> getNeedAirdropUserIds(@Param("index") int index, @Param("offset") int offset);

    List<Long> getBraceletNeedAirdropUserIds(@Param("index") int index, @Param("offset") int offset);

    int getNeedAirdropCount();

    int getBraceletNeedAirdropCount();

    int doUsersHaveDefaultCompute(@Param("userId") Long userId);

    double getDefeatComputePercent(@Param("userId") Long userId);
}
