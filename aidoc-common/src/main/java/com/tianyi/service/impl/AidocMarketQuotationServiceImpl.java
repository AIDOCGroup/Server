package com.tianyi.service.impl;

import com.tianyi.bo.AidocMarketQuotation;
import com.tianyi.mapper.AidocMarketQuotationMapper;
import com.tianyi.service.AidocMarketQuotationService;
import com.tianyi.vo.AidocMarketQuotationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * server
 *
 * @author GaoZhilai
 * @date 2018/4/23.
 */
@Service
public class AidocMarketQuotationServiceImpl implements AidocMarketQuotationService {
  @Autowired
  AidocMarketQuotationMapper aidocMarketQuotationMapper;

  @Override
  public List<AidocMarketQuotation> getAidocMarketQuotation(Map param) {
    return aidocMarketQuotationMapper.getAidocMarketQuotation(param);
  }

  @Override
  public int getAidocMarketQuotationCount(Map param) {
    return aidocMarketQuotationMapper.getAidocMarketQuotationCount(param);
  }

  /**
   * 获取最新一条有效的设置记录,按照更新时间倒序,最新一条
   * @return
   */
  @Override
  public AidocMarketQuotation getAvailableAidocMarketQuotation() {
    return aidocMarketQuotationMapper.getAvailableAidocMarketQuotation();
  }

  @Override
  public int addAidocMarketQuotation(AidocMarketQuotationVo aidocMarketQuotationVo) {
    return aidocMarketQuotationMapper.addAidocMarketQuotation(aidocMarketQuotationVo);
  }

  @Override
  public int updateAidocMarketQuotation(AidocMarketQuotationVo aidocMarketQuotationVo) {
    return aidocMarketQuotationMapper.updateAidocMarketQuotation(aidocMarketQuotationVo);
  }
}
