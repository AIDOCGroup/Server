package com.tianyi.mapper;

import com.tianyi.bo.AidocMarketQuotation;
import com.tianyi.vo.AidocMarketQuotationVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * server
 *
 * @author GaoZhilai
 * @date 2018/4/20.
 */
@Mapper
@Component
public interface AidocMarketQuotationMapper {
  List<AidocMarketQuotation> getAidocMarketQuotation(Map param);
  int getAidocMarketQuotationCount(Map param);
  AidocMarketQuotation getAvailableAidocMarketQuotation();
  int addAidocMarketQuotation(AidocMarketQuotationVo aidocMarketQuotationVo);
  int updateAidocMarketQuotation(AidocMarketQuotationVo aidocMarketQuotationVo);
}
