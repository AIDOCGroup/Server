package com.tianyi.service;

import com.alibaba.fastjson.JSON;
import com.tianyi.bo.AidocSet;
import com.tianyi.bo.UserStepAidocResult;
import com.tianyi.bo.enums.UserDayEnum;
import com.tianyi.dao.AidocSetDao;
import com.tianyi.mapper.AidocSetMapper;
import com.tianyi.util.DateUtil;
import com.tianyi.util.Tools;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

/**
 * Created by 雪峰 on 2018/1/16.
 */
@Service("aidocSetService")
@EnableScheduling
public class AidocSetService {
    public static final Logger logger = LogManager.getLogger(AidocSetService.class);
    @Resource
    AidocSetDao aidocSetDao;
    @Autowired
    AidocSetMapper aidocSetMapper;
    @Resource
    UserDayDataService userDayDataService;
    @Resource
    AccountService accountService;


    public void createAidocSet(long aiDocId, int type, String startDay, String endDay, int amount, long userId) {
        if (StringUtils.isEmpty(startDay)) {
            startDay = DateUtil.format(DateUtil.getCurrentDate(), DateUtil.C_DATE_PATTON_DEFAULT);
            endDay = DateUtil.format(DateUtil.getCurrentDate(), DateUtil.C_DATE_PATTON_DEFAULT);
        }


        if (type == 0) {
            List<AidocSet> aidocSets = aidocSetDao.getAidocSets(0, 0, Integer.MAX_VALUE, 0);
            for (AidocSet as : aidocSets) {
                as.setStatus(0);
                aidocSetDao.update(as);
            }
            addAidocSet(0, startDay, endDay, amount, userId);
        } else if (type == 1) {

            List<AidocSet> aidocSets = aidocSetDao.getAidocSetsByDay(1, startDay, 0);
            if (aidocSets != null && !aidocSets.isEmpty()) {
                throw new RuntimeException("时间冲突！！！");
            }
            aidocSets = aidocSetDao.getAidocSetsByDay(1, endDay, 0);
            if (aidocSets != null && !aidocSets.isEmpty()) {
                throw new RuntimeException("时间冲突！！！");
            }
            if (aiDocId > 0) {
                List<AidocSet> aidocSetsOld = aidocSetDao.getAidocSets(1, 0, Integer.MAX_VALUE, 0);
                for (AidocSet as : aidocSetsOld) {
                    if (as.getInvalidDate() != null && as.getInvalidDate().getTime() < DateUtil.getCurrentDate().getTime()) {
                        as.setStatus(0);
                        aidocSetDao.update(as);
                    }
                }
            }
            AidocSet aidocSet = aidocSetDao.getById(aiDocId);
            if (aidocSet == null) {
                addAidocSet(1, startDay, endDay, amount, userId);
            } else {
                editAidocSet(aidocSet, 1, startDay, endDay, amount, userId);
            }
        }


    }


    public List<AidocSet> getAidocSets(int type, int page, int pageSize) {
        return aidocSetDao.getAidocSets(type, page, pageSize, 1);
    }

    public Integer getTotalNumber(int type, int status) {
        return aidocSetDao.getTotalNumber(type, status);
    }

     //@Scheduled(cron = "0 59 23 ? * *")
    // @Scheduled(cron = "*/30 * * * * *")
/*    private void aidoc() {

        List<AidocSet> aidocSets = aidocSetDao.getAidocSetsByDay(1, DateUtil.format(DateUtil.getCurrentDate(), "yyyy-MM-dd"), 1);

        if (aidocSets == null || aidocSets.isEmpty()) {
            aidocSets = aidocSetDao.getAidocSets(0, 0, 10, 1);
        }

        int namu = 100;
        if (aidocSets != null && !aidocSets.isEmpty()) {
            namu = aidocSets.get(0).getAidocTotal();
        }


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.getCurrentDate());
        calendar.add(Calendar.DATE, -1);

        long stepTotal = userDayDataService.getTotlaNumberByDay(DateUtil.format(calendar.getTime(), DateUtil.C_DATE_PATTON_DEFAULT), UserDayEnum.STEPSNUM);

        if (stepTotal == 0) {
            return;
        }


        //用户获取数=今日总AIDOC/今日所有用户步数之和*本用户今日步数

        List<Object[]> userDayDataList = userDayDataService.getUserDayDatasDay(new Date());
            System.out.println("userDayDataList========"+ JSON.toJSONString(userDayDataList));
        for (Object[] vo : userDayDataList) {

            if (vo[0] == null || vo[1] == null) {
                continue;
            }


            BigInteger userId = (BigInteger) vo[0];
            BigDecimal total = (BigDecimal) vo[1];

            double ddd = Tools.getDecimalFour(namu * total.doubleValue() / stepTotal);

            if(ddd > 5){
                ddd = 5;
            }

            //System.out.println(stepTotal+"=="+namu+"===="+userId+"======"+total+"====="+ddd);
            //long userId, Integer coinNum,String channel,double coins

             accountService.coin(userId.longValue(),"give",(long)(ddd*1000));
        }


    }*/


    // @Scheduled(cron = "*/30 * * * * *")
   // @Scheduled(cron = "*/30 * * * * ?")//每30秒一次
    //@Scheduled(cron = "0 0/5 * * * ?")//每5分钟
    //@Scheduled(cron = "0 0 1 * * ?")//每天凌晨一点
    private void giveYesterdayAidoc() {
        Instant startTime = Instant.now();
        logger.info("【AIDOC】开始执行发币任务,当前日期为:{}" , DateTime.now().toString());
        List<AidocSet> aidocSets = aidocSetDao.getEffectiveAidocSets(1,1,10,1);
        if (aidocSets == null || aidocSets.isEmpty()) {
            aidocSets = aidocSetDao.getAidocSets(0, 0, 10, 1);
        }
        //可以送出的总的aidoc数量
        int namu = 100;
        int personalLimit = 5;
        if (aidocSets != null && !aidocSets.isEmpty()) {
//            namu = aidocSets.get(0).getAidocTotal();
            String tempSetValue = aidocSets.get(0).getSetValue();
            Integer tempLimit = aidocSets.get(0).getPersonalLimit();
            if (tempSetValue != null) {
                namu = Integer.parseInt(tempSetValue);
            }
            if(tempLimit != null && tempLimit != 0) {
                personalLimit = tempLimit;
            }
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.getCurrentDate());
        calendar.add(Calendar.DATE, -1);
        //昨天所有用户总步数
        long stepTotal = userDayDataService.getTotlaNumberByDay(DateUtil.format(calendar.getTime(), DateUtil.C_DATE_PATTON_DEFAULT), UserDayEnum.STEPSNUM);
        if (stepTotal == 0) {
            return;
        }
        //用户获取数=今日总AIDOC/今日所有用户步数之和*本用户今日步数
        //昨天的用户步数信息列表
        List<Object[]> userDayDataList = userDayDataService.getUserDayDatasDay(calendar.getTime());
        System.out.println("userDayDataList========"+ JSON.toJSONString(userDayDataList));
        for (Object[] vo : userDayDataList) {

            if (vo[0] == null || vo[1] == null) {
                continue;
            }
            BigInteger userId = (BigInteger) vo[0];
            BigDecimal total = (BigDecimal) vo[1];
            double ddd = Tools.getDecimalFour(namu * total.doubleValue() / stepTotal);
            if(ddd > personalLimit){
                ddd = personalLimit;
            }
            accountService.coin(userId.longValue(), "give", (long) (ddd * 1000));
        }
        Instant endTime = Instant.now();
        Interval period = new Interval(startTime, endTime);
        logger.info("【AIDOC】用户发币任务总可派发数量为{},每人派发上限为{}",namu,personalLimit);
        logger.info("【AIDOC】用户发币任务执行完毕，总耗时:{}", period.toPeriod().toString());
    }


    private void addAidocSet(int type, String startDay, String endDay, int amount, long userId) {
        AidocSet aidocSet = new AidocSet();
        aidocSet.setAidocTotal(amount);
        aidocSet.setEffectiveDate(DateUtil.parseDate(DateUtil.C_DATE_PATTON_DEFAULT, startDay));
        aidocSet.setInvalidDate(DateUtil.parseDate(DateUtil.C_DATE_PATTON_DEFAULT, endDay));
        aidocSet.setSetType(type);
        aidocSet.setStatus(0);
        aidocSet.setOperationUserId(userId);
        aidocSetDao.add(aidocSet);
    }


    private void editAidocSet(AidocSet aidocSet, int type, String startDay, String endDay, int amount, long userId) {
        aidocSet.setAidocTotal(amount);
        aidocSet.setEffectiveDate(DateUtil.parseDate(DateUtil.C_DATE_PATTON_DEFAULT, startDay));
        aidocSet.setInvalidDate(DateUtil.parseDate(DateUtil.C_DATE_PATTON_DEFAULT, endDay));
        aidocSet.setSetType(type);
        aidocSet.setOperationUserId(userId);
        aidocSetDao.update(aidocSet);
    }

    public List<UserStepAidocResult> getUserStepAidoc(String time, Integer page, Integer size,String keyword,Integer orderBy,Integer aidocOrder,Integer stepOrder){
        return aidocSetDao.getUserStepAidoc(time,page,size,keyword,orderBy,aidocOrder,stepOrder);
    }

    public List<UserStepAidocResult> getUserStepAidocTwo(Map param){
        return aidocSetMapper.getUserStepAidoc(param);
    }

    public int getUserStepAidocTotalTwo(Map param){
        return aidocSetMapper.getUserStepAidocTotal(param);
    }

    public int getUserStepAidocTotal(String time,String keyword){
        return aidocSetDao.getUserStepAidocPageNum(time,keyword);
    }

    public void exportStepAidocInfo(String time, Integer page, Integer size,String nickName,String userId,String mobile){
        List<String> listName=new ArrayList<>();
        listName.add("用户id");
        listName.add("昵称");
        listName.add("电话");
        listName.add("aidoc");
        listName.add("步数");
        listName.add("日期");
        List<String> listId = new ArrayList<>();
        listId.add("userId");
        listId.add("nickName");
        listId.add("mobile");
        listId.add("aidoc");
        listId.add("stepNumber");
        listId.add("day");
        //List<UserStepAidocResult> list=aidocSetDao.getUserStepAidoc(time,page,size,nickName,userId,mobile);
        //ExportBeanExcel<UserStepAidocResult> exportBeanExcelUtil = new ExportBeanExcel();
        //exportBeanExcelUtil.exportExcel("测试POI导出EXCEL文档",listName,listId,list);
    }

  public List<AidocSet> getAidocSet(Map param) {
    return aidocSetMapper.getAidocSet(param);
  }

  public int getAidocSetCount() {
    return aidocSetMapper.getAidocSetCount();
  }

  public int addAidocSet(AidocSet aidocSet) {
    return aidocSetMapper.addAidocSet(aidocSet);
  }

  public int updateAidocSet(AidocSet aidocSet) {
    return aidocSetMapper.updateAidocSet(aidocSet);
  }

  public AidocSet getAidocSetById(Long id) {
    return aidocSetMapper.getAidocSetById(id);
  }


  /**
   *
   * @param setType 要获取设置的类型,setType对应的类型描述见com.tianyi.bo.enums.SetTypeEnum
   * @return 返回有效时间内的配置,如果有多条有效同类型配置,按照时间降序返回最新一条设置.
   * AidocSet类关键字段注解
   * aidocTotal 该字段已经废弃,保留是为了兼容旧代码
   * setName 设置的描述,用户手动输入
   * setType 配置的类型,见对应枚举类
   * setValue 在当前配置类型下的数值
   * effectiveDate 生效日期
   * invalidDate 失效日期
   * status 配置是否有效,0为禁用,1为启用
   * personalLimit 当前配置的个人上限值,例如每个人最多得5个aidoc
   * operationUserId 更改配置的用户id
   */
   public AidocSet getAvailableAidocSet(int setType) {
     return aidocSetMapper.getAvailableAidocSet(setType);
   }
}
