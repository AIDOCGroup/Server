package me.aidoc.appserver.dao;

/**
 * @author vliu
 * @create 2018-10-09 15:32
 **/

import me.aidoc.appserver.po.CoinManage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface CoinManageMapper {

    Long insertCoinManage(CoinManage coinManage);

    List<CoinManage> selectCoinManageList();

    CoinManage selectCoinManageByDate(@Param("rewardDate")Date rewardDate);

    CoinManage selectCoinManageById(@Param("id")Long id);

    int updateCoinManage(CoinManage coinManage);
}
