package com.tianyi.mapper;

import com.tianyi.bo.AppVersion;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * server
 *
 * @author GaoZhilai
 * @date 2018/4/13.
 */
@Mapper
@Component
public interface AppVersionMapper {
  public abstract AppVersion getRencentDownloadUrl(Map param);
}
