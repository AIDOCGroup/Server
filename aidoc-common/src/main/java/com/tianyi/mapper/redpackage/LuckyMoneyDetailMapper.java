package com.tianyi.mapper.redpackage;

import com.tianyi.vo.redpackage.LuckyMoneyDetail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import java.util.List;

@Mapper
@Component
public interface LuckyMoneyDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LuckyMoneyDetail record);

    int insertSelective(LuckyMoneyDetail record);

    LuckyMoneyDetail selectByPrimaryKey(Long id);

    List<LuckyMoneyDetail> selectByParentId(Long id);

    int updateByPrimaryKeySelective(LuckyMoneyDetail record);

    int updateByPrimaryKey(LuckyMoneyDetail record);
}