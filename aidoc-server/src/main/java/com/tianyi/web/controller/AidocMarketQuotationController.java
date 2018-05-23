package com.tianyi.web.controller;

import com.tianyi.bo.AidocMarketQuotation;
import com.tianyi.bo.User;
import com.tianyi.service.AidocMarketQuotationService;
import com.tianyi.vo.AidocMarketQuotationVo;
import com.tianyi.web.AdminRequired;
import com.tianyi.web.model.PagedListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * server
 *
 * @author GaoZhilai
 * @date 2018/4/23.
 */
@RestController
@RequestMapping("/aidoc_market_quotation")
public class AidocMarketQuotationController {
  @Autowired
  AidocMarketQuotationService aidocMarketQuotationService;

  @AdminRequired
  @RequestMapping(value = "/get_aidoc_market_quotation",method = RequestMethod.POST)
  public PagedListModel getAidocMarketQuotation(@RequestBody AidocMarketQuotationVo aidocMarketQuotationVo){
    Integer page = aidocMarketQuotationVo.getPage();
    Integer size = aidocMarketQuotationVo.getSize();
    Map param = new HashMap();
    page = page == null ? 1 : page;
    size = size == null ? 20 : size;
    int offset = 0;
    if (page > 0) {
      offset = (page - 1) * size;
    }
    param.put("offset",offset);
    param.put("size",size);
    param.put("start_time",aidocMarketQuotationVo.getStart_time());
    param.put("end_time",aidocMarketQuotationVo.getEnd_time());
    param.put("id",aidocMarketQuotationVo.getId());
    List<AidocMarketQuotation> results = aidocMarketQuotationService.getAidocMarketQuotation(param);
    int count = aidocMarketQuotationService.getAidocMarketQuotationCount(param);
    return new PagedListModel(results,count,page,size);
  }

  @AdminRequired
  @RequestMapping(value = "/add_aidoc_market_quotation",method = RequestMethod.PUT)
  public Map addAidocMarketQuotation(@RequestBody AidocMarketQuotationVo aidocMarketQuotationVo,
                                     @Value("#{request.getAttribute('currentUser')}") User currentUser) {
    Map result = new HashMap(16);
    aidocMarketQuotationVo.setOperation_user_id(currentUser.getId());
    int effNum = aidocMarketQuotationService.addAidocMarketQuotation(aidocMarketQuotationVo);
    if (effNum > 0) {
      result.put("status","ok");
      result.put("msg","设置添加成功");
    } else {
      result.put("status","no");
      result.put("msg","设置添加失败");
    }
    return result;
  }

  @RequestMapping(value = "/update_aidoc_market_quotation",method = RequestMethod.POST)
  public Map updateAidocMarketQuotation(@RequestBody AidocMarketQuotationVo aidocMarketQuotationVo){
    Map result = new HashMap(16);
    int effNum = aidocMarketQuotationService.updateAidocMarketQuotation(aidocMarketQuotationVo);
    if (effNum > 0) {
      result.put("status","ok");
      result.put("msg","设置更新成功");
    } else {
      result.put("status","no");
      result.put("msg","设置更新失败,可能没有对应的配置id参数");
    }
    return result;
  }
}
