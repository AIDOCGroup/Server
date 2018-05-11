package com.tianyi.mapper;

import com.tianyi.vo.StepRankings;
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
public interface UserDayDataMapper {
  List<StepRankings> getRealTimeStepRankins(Map param);
}
