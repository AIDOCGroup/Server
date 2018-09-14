package com.tianyi.mapper;

import com.tianyi.bo.SmartDeviceFirmware;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author vliu
 * @create 2018-09-14 15:54
 **/
@Component
@Mapper
public interface SmartDeviceFirmwareMapper {

    /**
     *
     * @param firmware
     * @return
     */
    Long insertSmartDeviceFirmware(SmartDeviceFirmware firmware);

    /**
     * 根据设备类型查询当前设备的最新固件
     * @param type
     * @return
     */
    SmartDeviceFirmware selectCurrentFirmware(@Param("deviceType")Integer type);
}
