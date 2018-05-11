package com.tianyi.mapper;

import com.tianyi.bo.Account;
import com.tianyi.vo.AidocRankingVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * server
 *
 * @author GaoZhilai
 * @date 2018/4/17.
 */
@Mapper
@Component
public interface AccountMapper {
  Account getAccountByUserId(Long userId);
  List<AidocRankingVo> getAllAidocRanking(Map param);
  int getAllAidocRankingCount();
  List<AidocRankingVo> getFriendlyAidocRanking(Map param);
  int getFriendlyAidocRankingCount(Map param);
}
