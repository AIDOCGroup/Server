package com.tianyi.web.controller;

import com.tianyi.bo.EveryoneListEle;
import com.tianyi.bo.InvitationResult;
import com.tianyi.bo.enums.ChannelEnum;
import com.tianyi.framework.util.JSONUtils;
import com.tianyi.service.InvitationService;
import com.tianyi.util.EnumUtil;
import com.tianyi.util.StringUtil;
import com.tianyi.web.model.PagedListModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 邀请码相关接口
 * @author  GaoZhilai
 * @date 2018/3/27.
 */
@Api(value = "邀请码相关接口")
@RestController
@RequestMapping("/invitation")
public class InvitationController {
  public  final Logger logger = LogManager.getLogger(this.getClass());
  @Resource
  private InvitationService invitationService;

  @ApiOperation(value = "添加邀请码",notes = "为指定用户添加随机6位邀请码并返回邀请码内容")
  @ApiImplicitParams({
            @ApiImplicitParam(name = "user_id",value = "用户id",
                    dataType = "Long",paramType = "query"),
            @ApiImplicitParam(name = "status", value = "邀请码状态,0禁用,1启用",
                    dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "channel", value = "渠道,1微信,2微博,3电报群,4内测群",
                    dataType = "Integer", paramType = "query")
    })
  @RequestMapping(value = "addCustomInvitation", method = RequestMethod.GET)
  public Map addInvitationForUserId(@RequestParam(value = "user_id") Long userId,
                                    @RequestParam(value = "status") Integer invitationStatus,
                                    @RequestParam(value = "channel") Integer channel) {
    return invitationService.addCustomInvitation(userId
            /*,invitationCode*/, invitationStatus, channel);
  }

  @ApiOperation(value = "获取邀请码列表")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "p", value = "第几页",dataType = "Integer", paramType = "query"),
          @ApiImplicitParam(name = "p_size", value = "每页显示条数",
                  dataType = "Integer", paramType = "query")
    })
  @RequestMapping(value = "getInvitationList", method = RequestMethod.GET)
  public PagedListModel getInvitationList(@RequestParam(value = "p", required = false) Integer page,
                                            @RequestParam(value = "p_size", required = false) Integer pageSize,
                                          @RequestParam(value = "invi_num_order", required = false) Integer inviNumOrder) {
    page = page == null ? 1 : page;
    pageSize = pageSize == null ? 20 : pageSize;
    List<InvitationResult> result = invitationService.getAllInvitationTwo(page, pageSize,inviNumOrder);
    int total = invitationService.getInvitationTotal();
    return new PagedListModel<>(result, total, page, pageSize);
  }

  @ApiOperation(value = "启用禁用邀请码", notes = "设置邀请码状态,启用禁用邀请码")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "invitation_code", value = "要设置状态的邀请码",
                  dataType = "String", paramType = "query"),
          @ApiImplicitParam(name = "status",value = "状态", dataType = "Integer", paramType = "query")
  })
  @RequestMapping(value = "setInvitationStatus", method = RequestMethod.GET)
  public Map setInvitationStatus(@RequestParam("invitation_code") String invitationCode,
                                 @RequestParam("status") Integer invitationStatus) {
    int effNumber = invitationService.setInvitationStatus(invitationCode, invitationStatus);
    Map<String,Map<String,String>> resultList = new HashMap<>(16);
    Map<String,String> result = new HashMap<>(16);
    if (invitationStatus == 0 && effNumber > 0) {
      result.put("status", "ok");
      result.put("msg", "邀请码" + invitationCode + "已经成功禁止");
    } else if (invitationStatus == 1 && effNumber > 0) {
      result.put("status", "ok");
      result.put("msg", "邀请码" + invitationCode + "已经成功启用");
    } else {
      result.put("status", "no");
      result.put("msg", "修改失败,status值应为0或1");
    }
    resultList.put("list", result);
    return resultList;
  }

  @RequestMapping("getChannelList")
  public Map getChannelList() {
    Map<String,List<Map<String,Object>>> result = new HashMap<>(16);
    List<Map<String,Object>> resultList = new ArrayList<>();
    Map channelMap = EnumUtil.EnumToMap(ChannelEnum.class);
    Set keys = channelMap.keySet();
    for (Object key :
            keys) {
      Map<String,Object> temp = new HashMap<>(16);
      temp.put("id", key);
      temp.put("value", channelMap.get(key));
      resultList.add(temp);
    }
    result.put("list", resultList);
    return result;
  }

  @ApiOperation(value = "获取运营人员列表")
  @RequestMapping("getOperateList")
  public Map getOprateList() {
    Map<String,List> hashMap = new HashMap<>(16);
    List result = invitationService.getOperateList();
    hashMap.put("list", result);
    return hashMap;
  }

  @ApiOperation(value = "获取目前总的邀请人数")
  @RequestMapping("getAllInvitationNum")
  public Map getAllInvitationNum() {
    Map<String,Integer> result = new HashMap<>(16);
    int total = invitationService.getAllInvitationNum();
    result.put("total", total);
    return result;
  }

  /**
    * 获取当前编辑的邀请码信息
    * @param invitationCode 要查询的邀请码
    * @return 返回当前编辑邀请码信息
    */
  @ApiOperation(value = "获取当前要编辑的邀请码详细信息")
  @ApiImplicitParam(name = "invitation_code", value = "要编辑的邀请码值",
          dataType = "String", paramType = "query")
  @RequestMapping(value = "getEditInvitation", method = RequestMethod.GET)
  public Map getEditInvitation(@RequestParam(value = "invitation_code") String invitationCode) {
    Map<String,List> result = new HashMap<>(16);
    List<InvitationResult> rlist = invitationService.getEditInvitationTwo(invitationCode);
    result.put("list", rlist);
    return result;
  }

  @ApiOperation(value = "获取我的邀请码详情", notes = "获取我的邀请码详情,"
          + "包括算力值,一级邀请数,二级邀请数,一级邀请用户列表,我的邀请码列表")
  @ApiImplicitParam(name = "user_id", value = "用户id",dataType = "String", paramType = "query")
  @RequestMapping(value = "getMyInvitationDetail",method = RequestMethod.GET)
  public Map getMyInvitationDetail(@RequestParam(value = "user_id")Long userId){
    Map result = invitationService.getMyInvitationDetail(userId);
    return result;
  }

  @ApiOperation(value = "获取看看大家运气列表")
  @ApiImplicitParam(name = "invitation_code", value = "邀请码",dataType = "String",paramType = "query")
  @RequestMapping(value = "getEveryoneList", method = RequestMethod.GET)
  public Map getEveryoneList(@RequestParam(value = "invitation_code") String invitationCode){
    Map result = new HashMap();
    if (!StringUtil.isStringEmpty(invitationCode)) {
      List<EveryoneListEle> lists= invitationService.getEveryList(invitationCode);
      logger.info("fetch invitation list size is :{}",
          CollectionUtils.isNotEmpty(lists) ? JSONUtils.writeValueAsString(lists) : 0);
      result.put("status","ok");
      result.put("list",lists);
    } else {
      result.put("status","no");
      result.put("msg","邀请码不能为空");
    }
    return result;
  }
}
