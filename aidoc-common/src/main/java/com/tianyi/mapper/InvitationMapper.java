package com.tianyi.mapper;

import com.tianyi.bo.EveryoneListEle;
import com.tianyi.bo.Invitation;
import com.tianyi.bo.InvitationResult;
import com.tianyi.bo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by gaozhilai on 2018/4/2.
 */
@Mapper
@Component
public interface InvitationMapper {
    public abstract int addCustomInvitation(Invitation param);
    public abstract int setInvitationStatus(Map param);
    public abstract Invitation getInvitationByCode(Map param);
    public abstract List<InvitationResult> getEditInvitation(Map param);
    public abstract int getInvitationNum(Map param);
    public abstract List<InvitationResult> getAllInvitation(Map param);
    public abstract int getInvitationTotal();
    public abstract int getAllInvitationNum();
    public abstract List<User> getAllInvitationUser(Map param);
    public abstract List<Invitation> getInvitationListById(Map param);
    public abstract List<EveryoneListEle> getEveryoneList(String invitationCode);
}
