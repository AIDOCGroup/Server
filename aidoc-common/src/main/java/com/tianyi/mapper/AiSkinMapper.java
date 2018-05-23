package com.tianyi.mapper;

import com.tianyi.vo.AiSkinVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * author:CDH
 * Date:2018/5/11
 */
@Mapper
@Component
public interface AiSkinMapper {

    void insertAiSkin(AiSkinVo aiSkinVo);
}
