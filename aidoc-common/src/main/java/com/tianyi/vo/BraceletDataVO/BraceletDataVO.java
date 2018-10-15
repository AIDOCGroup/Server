package me.aidoc.appserver.vo.smartdevice;

import lombok.Data;

import java.util.List;

/**
 * @author vliu
 * @create 2018-08-09 17:50
 * 手环上传的数据
 **/
@Data
public class BraceletDataVO {

    /**
     * 数据日期,格式为: yyyy-MM:-dd
     */
    private String date;

    /**
     * 步数
     */
    private Integer stepNumber;

    /**
     * 卡路里
     */
    private Integer calorie;

    /**
     * 里程
     */
    private Double mileage;

    /**
     * 睡眠时长
     */
    private Long sleepTime;

    /**
     * 清醒次数
     */
    private Integer wakingTime;

    /**
     * 入睡时间,格式为 HH:mm
     */
    private String toSleepTime;

    /**
     * 起床时间，格式为  HH:mm
     */
    private String getUpTime;

    private List<BraceletHeartRateVO> heartRates;

    private List<BraceletSleepMegInfoVO> sleepInfo;
}
