package me.aidoc.appserver.service.redpackage;


import me.aidoc.appserver.common.PageData;
import me.aidoc.appserver.common.exception.ServerBizException;
import me.aidoc.appserver.po.user.AccountRedPacket;
import java.math.BigDecimal;

/**
 * @author vliu
 * @create 2018-08-23 18:32
 **/
public interface RedPackageService {

    /**
     * 发送红包
     * @param senderId    发送者用户ID
     * @param amount      红包金额
     * @param count       红包数量
     * @param comments    红包留言
     * @param type        红包类型
     */
    void sendRedPackage(Long senderId, BigDecimal amount,Integer count,String comments,AccountRedPacket.RedPacketType type) throws ServerBizException;


    /**
     * 查询一个红包
     * @param businessNo
     * @return
     */
    AccountRedPacket getRedPackage(String businessNo);

    /**
     * 查询红包的领取信息
     * @param businessNo
     */
    void getRedPackageReceiveInfo(String businessNo);

    /**
     * 查询红包列表
     * @param userId
     * @param page
     * @param size
     * @return
     */
    PageData getRedPackageList(Long userId, Integer page, Integer size);

    /**
     * 查询用户的钱包红包是否激活
     * @param userId
     * @return
     */
    boolean isActive(Long userId);

    /**
     *
     * @param userId    需要发送验证码的用户Id
     * @param validate  网易滑块验证的验证码
     */
    void sendSms(Long userId,String validate) throws ServerBizException;

    /**
     * 领取红包
     * @param businessNo    被领取红包的业务ID
     * @param receiverId    领取红包者的用户ID
     */
    void cashingRedPackage(String businessNo,Long receiverId) throws ServerBizException;
}
