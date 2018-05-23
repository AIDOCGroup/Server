package com.tianyi.web.controller;

import com.tianyi.service.UserDayDataService;
import com.tianyi.vo.StepRankings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * server
 *
 * @author GaoZhilai
 * @date 2018/4/20.
 */
@RestController
@RequestMapping(value = "/internal")
public class InternalUseController {
  @Autowired
  UserDayDataService userDayDataService;

  @RequestMapping(value = "/get_real_time_step_ranking")
  public List<StepRankings> getRealTimeStepRankins(@RequestParam(value = "p",required = false) Integer page,
                                                   @RequestParam(value = "p_size",required = false) Integer size) {
    Map param = new HashMap();
    page = page == null ? 1 : page;
    size = size == null ? 20 : size;
    int offset = 0;
    if (page > 0) {
      offset = (page - 1) * size;
    }
    param.put("offset",offset);
    param.put("size",size);
    return userDayDataService.getRealTimeStepRankings(param);
  }
}
