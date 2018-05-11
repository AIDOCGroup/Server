package com.tianyi.mapper;

import com.tianyi.bo.AidocSet;
import com.tianyi.bo.UserStepAidocResult;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * server
 *
 * @author GaoZhilai
 * @date 2018/4/13.
 */
@Mapper
@Component
public interface AidocSetMapper {
  List<AidocSet> getAidocSet(Map param);
  int addAidocSet(AidocSet aidocSet);
  int updateAidocSet(AidocSet aidocSet);
  int getAidocSetCount();
  List<UserStepAidocResult> getUserStepAidoc(Map param);
  int getUserStepAidocTotal(Map param);
  AidocSet getAidocSetById(Long id);
  AidocSet getAvailableAidocSet(int setType);
}
