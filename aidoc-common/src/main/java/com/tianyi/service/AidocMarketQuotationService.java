package com.tianyi.service;

import com.tianyi.bo.AidocMarketQuotation;
import com.tianyi.vo.AidocMarketQuotationVo;

import java.util.List;
import java.util.Map;

/**
 * server
 *
 * @author GaoZhilai
 * @date 2018/4/23.
 */
public interface AidocMarketQuotationService {
  List<AidocMarketQuotation> getAidocMarketQuotation(Map param);
  int getAidocMarketQuotationCount(Map param);
  AidocMarketQuotation getAvailableAidocMarketQuotation();
  int addAidocMarketQuotation(AidocMarketQuotationVo aidocMarketQuotationVo);
  int updateAidocMarketQuotation(AidocMarketQuotationVo aidocMarketQuotationVo);
}
