package com.tianyi.service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.tianyi.bo.EveryoneListEle;
import com.tianyi.bo.Invitation;
import com.tianyi.bo.InvitationResult;
import com.tianyi.bo.User;
import com.tianyi.bo.enums.ChannelEnum;
import com.tianyi.bo.enums.LanguageEnum;
import com.tianyi.dao.InvitationDao;
import com.tianyi.dao.UserDao;
import com.tianyi.mapper.AccountDetailMapper;
import com.tianyi.mapper.InvitationMapper;
import com.tianyi.mapper.UserMapper;
import com.tianyi.util.InvitationUtil;
import com.tianyi.util.Send253SMS;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import com.tianyi.vo.ThirdPartyVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by gaozhilai on 2018/3/27.
 */
@Service("invitationService")
public class InvitationService {
    public static final Logger logger = LogManager.getLogger(InvitationService.class);

    @Resource
    InvitationDao invitationDao;
    @Resource
    UserDao userDao;
    @Autowired
    InvitationMapper invitationMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    AccountDetailMapper accountDetailMapper;
    @Autowired
    AccountService accountService;
    @Autowired
    Send253SMS send253SMS;

    public Map addCustomInvitation(Long userId,/*String invitationCode,*/Integer status,Integer channel){
        Invitation invitation=new Invitation();
        invitation.setUpdatedOn(new Date());
        invitation.setCreatedOn(new Date());
        invitation.setUpdatedTimestamp(System.currentTimeMillis());
        invitation.setUserId(userId);
//        invitation.setInvitationCode(invitationCode);
        invitation.setInvitationCode(InvitationUtil.generateShortUuid());
        invitation.setStatus(status);
        invitation.setChannel(channel);

        Map result=new HashMap();
        if (!userDao.isUserExist(userId)){
            result.put("status","no");
            result.put("msg","用户id不存在,请联系管理员");
            return result;
        }
        if(isInvitationExist(invitation.getInvitationCode())){
            result.put("status","no");
            result.put("msg","已经存在相同邀请码,请更换邀请码");
            return result;
        }
        //invitationDao.addCustomInvitation(userId,invitationCode,status,channel);
        int effNum=invitationMapper.addCustomInvitation(invitation);
        if(effNum>0){
            result.put("status","ok");
            result.put("msg","邀请码添加成功");
            result.put("content",invitation.getInvitationCode());
        }else{
            result.put("status","no");
            result.put("msg","添加失败,请联系开发人员");
        }
        return result;
    }

    public boolean isInvitationExist(String invitationCode){
        Map param=new HashMap();
        param.put("invitationCode",invitationCode);
        Invitation invitation=invitationMapper.getInvitationByCode(param);
        return invitation!=null?true:false;
    }

    public int setInvitationStatus(String invitationCode,int status){
        Map param=new HashMap();
        param.put("invitationCode",invitationCode);
        param.put("status",status);
        int effNumber=invitationMapper.setInvitationStatus(param);
        //invitationDao.setInvitationStatus(invitationCode,status);
        return effNumber;
    }

    public List<InvitationResult> getAllInvitationTwo(Integer page,Integer size,Integer inviNumOrder){
        Map param=new HashMap();
        int offset=0;
        if(page!=null&&size!=null){
            if(page>0){
                offset=(page-1)*size;
            }
        }
        param.put("offset",offset);
        param.put("size",size);
        param.put("inviNumOrder",inviNumOrder);
        List<InvitationResult> result=invitationMapper.getAllInvitation(param);
        for (int i=0;i<result.size();i++) {
            Map tempParam=new HashMap();
            InvitationResult temp=result.get(i);
            tempParam.put("invitationCode",temp.getInvitationCode());
            //temp.setInvitationNum(invitationMapper.getInvitationNum(tempParam));
            if(temp.getInvitationChannelTemp()!=null){
                temp.setInvitationChannel(temp.getInvitationChannelTemp().getDescription());
            }
            if(temp.getInvitationStatusTemp()!=null){
                temp.setInvitationStatus(temp.getInvitationStatusTemp().getDescription());
            }
            temp.setOrdernumber(i+offset);
        }
        return result;
    }

    public int getInvitationTotal(){
        return invitationMapper.getInvitationTotal();
    }

    public List getOperateList(){
        return invitationDao.getOperateList();
    }

    public int getAllInvitationNum(){
        return invitationMapper.getAllInvitationNum();
    }

    public List<InvitationResult> getEditInvitationTwo(String invitationCode){
        HashMap param=new HashMap();
        param.put("invitationCode",invitationCode);
        List<InvitationResult> invitationList=invitationMapper.getEditInvitation(param);
        for (InvitationResult ele :
                invitationList) {
            ele.setInvitationNum(invitationMapper.getInvitationNum(param));
          if(ele.getInvitationChannelTemp()!=null){
            ele.setInvitationChannel(ele.getInvitationChannelTemp().getDescription());
          }
          if(ele.getInvitationStatusTemp()!=null){
            ele.setInvitationStatus(ele.getInvitationStatusTemp().getDescription());
          }
        }
        return invitationList;
    }

  public Map getMyInvitationDetail(Long userId){
    Map<String,String> userIdParam = new HashMap<>(16);
    userIdParam.put("userId",userId+"");
    User myself = userMapper.getUserById(userId);
    List<Invitation> myInvitationList = invitationMapper.getInvitationListById(userIdParam);
    int firstLevel = 0;
    int secondLevel = 0;
    Long invitationReward = 0L;
    List firstLevelList = new ArrayList();
    if (myInvitationList.size() > 0) {
      for (Invitation myInvitationEle :
              myInvitationList) {
        Map<String,String> invitationParam = new HashMap<>(16);
        invitationParam.put("invitationCode",myInvitationEle.getInvitationCode());
        List<User> firstLevelUser = invitationMapper.getAllInvitationUser(invitationParam);
        firstLevelList.add(firstLevelUser);
        firstLevel += firstLevelUser.size();
        for (User flUserele:
                firstLevelUser) {
                    Map<String,String> flUserParam = new HashMap<>(16);
                    flUserParam.put("userId",flUserele.getId()+"");
                    List<Invitation> flInvitation = invitationMapper.getInvitationListById(flUserParam);
          for (Invitation flInvitationEle :
                  flInvitation) {
            Map<String,String> secondInviParam = new HashMap<>(16);
            secondInviParam.put("invitationCode",flInvitationEle.getInvitationCode());
            int secondNumTemp = invitationMapper.getInvitationNum(secondInviParam);
            secondLevel += secondNumTemp;
          }
        }
      }
        Long accountId = accountDetailMapper.getAccountIdByUserId(userId).getId();
        invitationReward = accountDetailMapper.getInvitationReward(accountId);
    } else {
      Invitation invitation=addDefaultInvitation(userId);
      myInvitationList.add(invitation);
    }
    Map result = new HashMap();
    result.put("invitation_reward",invitationReward);
    result.put("first_level_num",firstLevel);
    result.put("second_level_num",secondLevel);
    result.put("first_level_list",firstLevelList);
    result.put("my_invitation_list",myInvitationList);
    result.put("nickname",myself.getNickname());
    return result;
  }

  public Invitation addDefaultInvitation(Long userId) {
    String invitation = InvitationUtil.generateShortUuid();
    if (isInvitationExist(invitation)) {
      invitation = InvitationUtil.generateShortUuid();
    }
    Invitation insertInvitation=new Invitation();
    insertInvitation.setStatus(1);
    insertInvitation.setUserId(userId);
    insertInvitation.setInvitationCode(invitation);
    insertInvitation.setChannel(ChannelEnum.Client.getCode());
    insertInvitation.setUpdatedTimestamp(System.currentTimeMillis());
    insertInvitation.setCreatedOn(new Date());
    insertInvitation.setUpdatedOn(new Date());
    invitationMapper.addCustomInvitation(insertInvitation);
    return insertInvitation;
  }

  public void invitationRewardAidoc(String invitationCode) {
    Map inviParam = new HashMap();
    inviParam.put("invitationCode",invitationCode);
    Invitation invitation = invitationMapper.getInvitationByCode(inviParam);
    int rewardNum = invitationMapper.getAllInvitationUser(inviParam).size();
    Long userId=invitation.getUserId();
    //一级奖励
//      if (rewardNum<50) {
          //一级奖励上限50人
          accountService.consumeCoin(userId,"invitation1",10000, DateTime.now().toDate());
//      }
      //二级奖励
      /*User user = userMapper.getUserById(userId);
      String secondInvitationCode = user.getRegistInvitationCode();
      if (secondInvitationCode!=null) {
        Map secondInviParam = new HashMap();
        secondInviParam.put("invitationCode",secondInvitationCode);
        Invitation secondInvitation = invitationMapper.getInvitationByCode(secondInviParam);
        Long secondUserId = secondInvitation.getUserId();
        logger.info("测试secondUserId:::{}",secondUserId);
        accountService.consumeCoin(secondUserId,"invitation2",5000);
    }*/
  }

  public List<EveryoneListEle> getEveryList(String invitationCode){
      List<EveryoneListEle> result = invitationMapper.getEveryoneList(invitationCode);
      return result;
  }

  public SendSmsResponse sendDefaultPassword(int countryCode,String phone, String defaultPassword, String lang){
    LanguageEnum temp = LanguageEnum.valueOf(lang);
    return send253SMS.sendDefaultPassword(countryCode,phone,defaultPassword,temp);
  }

  public Invitation getInvitationByCode(Map param){
      return invitationMapper.getInvitationByCode(param);
  }

    public User getOpenIdUser(long userId) {
        User openIdUser = userMapper.getOpenIdUser(userId);
        return openIdUser;
    }

    public ThirdPartyVo getUserPhone(String phone) {
        ThirdPartyVo userPhone = userMapper.getUserPhone(phone);
        return userPhone;
    }
}
