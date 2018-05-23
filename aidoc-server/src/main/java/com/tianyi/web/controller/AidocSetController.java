package com.tianyi.web.controller;

import com.tianyi.bo.AidocSet;
import com.tianyi.bo.User;
import com.tianyi.bo.UserStepAidocResult;
import com.tianyi.bo.enums.SetTypeEnum;
import com.tianyi.service.AidocSetService;
import com.tianyi.util.DateUtil;
import com.tianyi.util.EnumUtil;
import com.tianyi.util.JsonPathArg;
import com.tianyi.web.AuthRequired;
import com.tianyi.web.model.ActionResult;
import com.tianyi.web.model.PagedListModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 雪峰 on 2018/1/18.
 */
@RestController
public class AidocSetController {
    public static final Logger logger = LogManager.getLogger(AidocSetController.class);

    @Resource
    AidocSetService aidocSetService;

    @AuthRequired
    @RequestMapping(value = "/manager/aidoc", method = RequestMethod.POST)
    public ActionResult addAidocSet(
                                        @JsonPathArg("type") int type,
                                       @JsonPathArg("startDay") String startDay,
                                       @JsonPathArg("endDay") String endDay,
                                       @JsonPathArg("amount") int amount,
                                       @Value("#{request.getAttribute('currentUser')}") User currentUser) {

        aidocSetService.createAidocSet(0,type,startDay,endDay,amount,currentUser.getId());


        return new ActionResult();
    }

    @AuthRequired
    @RequestMapping(value = "/manager/aidoc/{aidoc_id}", method = RequestMethod.PUT)
    public ActionResult editAidoc(@PathVariable("aidoc_id") int aidocId,
                                         @JsonPathArg("type") int type,
                                         @JsonPathArg("startDay") String startDay,
                                         @JsonPathArg("endDay") String endDay,
                                         @JsonPathArg("amount") int amount,
                                       @Value("#{request.getAttribute('currentUser')}") User currentUser) {
        aidocSetService.createAidocSet(aidocId,type,startDay,endDay,amount,currentUser.getId());
        return new ActionResult();
    }



    @AuthRequired
    @RequestMapping(value = "" +
            "/manager/aidoc/{type}", method = RequestMethod.GET)
    public PagedListModel<List<Map<String, Object>>> getaIdocs(@PathVariable("type") int type,
                                                             @RequestParam(value = "p", required = false) Integer page,
                                                             @RequestParam(value = "p_size", required = false) Integer pageSize) {
        page = page == null ? 1 : page;
        pageSize = pageSize == null ? 20 : pageSize;
        List<Map<String, Object>> result = new ArrayList<>();

        List<AidocSet> aidocSets = aidocSetService.getAidocSets(type,page,pageSize);
        long total = aidocSetService.getTotalNumber(type,1);

        for(AidocSet as:aidocSets){
            Map<String,Object> map = new HashMap<>();
            map.put("id",as.getId());
            map.put("startDay", DateUtil.formatTime(as.getEffectiveDate()));
            map.put("endDay",DateUtil.formatTime(as.getInvalidDate()));
            map.put("amount",as.getAidocTotal());
            map.put("type",as.getSetType());

            result.add(map);
        }

        return new PagedListModel(result, total, page, pageSize);
    }

    @AuthRequired
    @RequestMapping(value = "/manager/aidoc/formula", method = RequestMethod.GET)
    public Map<String, Object> getFormula(@RequestParam(value = "type", required = false) Integer type) {
        type = type == null ? 0 : type;

            Map<String,Object> map = new HashMap<>();
            map.put("formula","weight*step*0.5*1.036");
        return map;
    }

    @AuthRequired
    @ResponseBody
    @RequestMapping(value = "/getStepAidocInfo", method = RequestMethod.GET)
    public PagedListModel getStepAidocInfo(@RequestParam(value = "time",required = false) String time,
                                           @RequestParam(value = "p") Integer page,@RequestParam(value = "p_size") Integer size,
                                           /*@RequestParam(value = "nick_name",required = false) String nickName,
                                           @RequestParam(value = "user_id",required = false) String userId,*/
                                           @RequestParam(value = "keyword",required = false) String keyword,
                                           @RequestParam(value = "timeorder",required = false)Integer orderBy,
                                           @RequestParam(value = "aidocorder",required = false)Integer aidocOrder,
                                           @RequestParam(value = "steporder",required = false)Integer stepOrder){
        List<UserStepAidocResult> result=aidocSetService.getUserStepAidoc(time,page,size,keyword,orderBy,aidocOrder,stepOrder);
        int total=aidocSetService.getUserStepAidocTotal(time,keyword);
        return new PagedListModel(result, total, total, size);
    }

  @ResponseBody
  @RequestMapping(value = "/getStepAidocInfoTwo", method = RequestMethod.GET)
  public PagedListModel getStepAidocInfoTwo(@RequestParam(value = "time",required = false) String time,
                               @RequestParam(value = "p",required = false) Integer page,@RequestParam(value = "p_size",required = false) Integer size,
                               @RequestParam(value = "keyword",required = false) String keyword,
                               @RequestParam(value = "timeorder",required = false)Integer timeOrder,
                               @RequestParam(value = "aidocorder",required = false)Integer aidocOrder,
                               @RequestParam(value = "steporder",required = false)Integer stepOrder){
      Map param = new HashMap();
      page = page == null ? 1 : page;
      size = size == null ? 20 : size;
      int offset = 0;
      if (page > 0) {
        offset = (page - 1) * size;
      }
      if (keyword != null && !"undefined".equals(keyword)) {
        param.put("keyWord",keyword);
      }
      param.put("timeOrder",timeOrder);
      param.put("aidocOrder",aidocOrder);
      param.put("stepOrder",stepOrder);
      param.put("time",time);
      param.put("offset",offset);
      param.put("size",size);
//      logger.info("参数keyword为:{},timeorder为:{},aidocorder为:{},steporder为:{},time为:{},offset为:{},size为:{}",
//              keyword,timeOrder,aidocOrder,stepOrder,time,offset,size);
      List<UserStepAidocResult> result= aidocSetService.getUserStepAidocTwo(param);
      int total=aidocSetService.getUserStepAidocTotalTwo(param);
      return new PagedListModel(result, total, page, size);
  }

    @AuthRequired
    @ResponseBody
    @RequestMapping(value = "/exportStepAidocInfo", method = RequestMethod.GET)
    public String exportStepAidoc2Excel(@RequestParam(value = "time",required = false) String time,
                                        @RequestParam(value = "p") Integer page,@RequestParam(value = "p_size") Integer size,
                                        @RequestParam(value = "nick_name",required = false) String nickName,
                                        @RequestParam(value = "user_id",required = false) String userId,
                                        @RequestParam(value = "mobile",required = false) String mobile){
        aidocSetService.exportStepAidocInfo(time,page,size,nickName,userId,mobile);
        return "测试导出excel";
    }

  @ResponseBody
  @RequestMapping(value = "/aidoc_set/get_aidoc_set", method = RequestMethod.GET)
  public PagedListModel getAidocSet(@RequestParam(value = "p",required = false) Integer page,
                                    @RequestParam(value = "p_size",required = false) Integer size) {
    page = page == null ? 1 : page;
    size = size == null ? 20 : size;
    int offset = 0;
    if (page > 0) {
      offset = (page - 1) * size;
    }
    Map param = new HashMap(16);
    param.put("offset",offset);
    param.put("size",size);
    List<AidocSet> aidocSets = aidocSetService.getAidocSet(param);
    int total = aidocSetService.getAidocSetCount();
    return new PagedListModel(aidocSets,total,page,size);
  }

  @AuthRequired
  @ResponseBody
  @RequestMapping("/aidoc_set/add_aidoc_set")
  public Map addAidocSet(@RequestParam(value = "set_name") String setName,
                         @RequestParam(value = "effective_date") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date effectiveDate,
                         @RequestParam(value = "invalid_date") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date invalidDate,
                         @RequestParam(value = "set_value") String setValue,
                         @RequestParam(value = "personal_limit")Integer personalLimit,
                         @RequestParam(value = "status")Integer status,
                         @RequestParam(value = "set_type") Integer setType,
                         @Value("#{request.getAttribute('currentUser')}") User currentUser){
//  public Map addAidocSet(@RequestBody AidocSet aidocSet){
        AidocSet aidocSet = new AidocSet();
        aidocSet.setSetName(setName);
        aidocSet.setEffectiveDate(effectiveDate);
        aidocSet.setInvalidDate(invalidDate);
        aidocSet.setSetValue(setValue);
        aidocSet.setPersonalLimit(personalLimit);
        aidocSet.setStatus(status);
        aidocSet.setSetType(setType);
        aidocSet.setOperationUserId(currentUser.getId());
        int effNum = aidocSetService.addAidocSet(aidocSet);
        Map result = new HashMap(16);
        if (effNum > 0) {
            result.put("status","ok");
            result.put("msg","添加成功");
        } else {
            result.put("status","no");
            result.put("msg","添加失败,请联系管理员");
        }
        return result;
  }

  @ResponseBody
  @RequestMapping("/aidoc_set/get_aidoc_set_type")
  public Map getAidocSetType(){
    Map<String,List<Map<String,Object>>> result = new HashMap<>(16);
    List<Map<String,Object>> resultList = new ArrayList<>();
    Map setType = EnumUtil.EnumToMap(SetTypeEnum.class);
    Set keys = setType.keySet();
    for (Object key :
            keys) {
      Map<String,Object> temp = new HashMap<>(16);
      temp.put("id", key);
      temp.put("value", setType.get(key));
      resultList.add(temp);
    }
    result.put("list", resultList);
    return result;
  }

  @AuthRequired
  @ResponseBody
  @RequestMapping("/aidoc_set/update_aidoc_set")
  public Map updateAidocSet(@RequestParam(value = "set_id")Long id,
                            @RequestParam(value = "set_name") String setName,
                            @RequestParam(value = "effective_date") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date effectiveDate,
                            @RequestParam(value = "invalid_date") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date invalidDate,
                            @RequestParam(value = "set_value") String setValue,
                            @RequestParam(value = "personal_limit")Integer personalLimit,
                            @RequestParam(value = "status")Integer status,
                            @RequestParam(value = "set_type") Integer setType,
                            @Value("#{request.getAttribute('currentUser')}") User currentUser){
    AidocSet aidocSet = new AidocSet();
    aidocSet.setId(id);
    aidocSet.setSetName(setName);
    aidocSet.setEffectiveDate(effectiveDate);
    aidocSet.setInvalidDate(invalidDate);
    if(setType == SetTypeEnum.AidocTotal.getCode()){
      //如果要设置的是aidoc发放总数,为原先的aidoc_total字段也赋值赋成setValue兼容原先的接口
      aidocSet.setAidocTotal(Integer.parseInt(setValue));
    }
    aidocSet.setSetType(setType);
    aidocSet.setSetValue(setValue);
    aidocSet.setPersonalLimit(personalLimit);
    aidocSet.setStatus(status);
    aidocSet.setOperationUserId(currentUser.getId());
    Map result = new HashMap();
    int effNum = aidocSetService.updateAidocSet(aidocSet);
    if (effNum > 0) {
      result.put("status","ok");
      result.put("msg","配置更新成功");
    }else {
      result.put("status","no");
      result.put("msg","配置更新失败");
    }
    return result;
  }

  @RequestMapping("/aidoc_set/get_aidoc_set_by_id")
  @ResponseBody
  public Map getAidocSetById(@RequestParam(value = "id")Long id){
      Map result = new HashMap();
      AidocSet aidocSet = aidocSetService.getAidocSetById(id);
      if(aidocSet!=null) {
        result.put("status","ok");
        result.put("msg","已查询到数据");
        result.put("content",aidocSet);
      }else{
        result.put("status","no");
        result.put("msg","无此id对应的配置数据");
        result.put("content",null);
      }
      return result;
  }
}
