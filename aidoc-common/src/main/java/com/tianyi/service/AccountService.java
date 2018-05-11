package com.tianyi.service;

import com.tianyi.bo.Account;
import com.tianyi.bo.AccountDetail;
import com.tianyi.dao.AccountDao;
import com.tianyi.dao.AccountDetailDao;
import com.tianyi.mapper.AccountMapper;
import com.tianyi.vo.AidocRankingVo;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 雪峰 on 2018/1/16.
 */
@Service("accountService")
public class AccountService {
    @Resource
    AccountDao accountDao;
    @Resource
    AccountDetailDao accountDetailDao;
    @Autowired
    AccountMapper accountMapper;



    public Account getAccountByUserId(long userId){
        return accountDao.getAccountByUserId(userId);
    }

    public List<AccountDetail> getAccountDetails(final long userId , final int page, final int pageSize){

        Account account = accountDao.getAccountByUserId(userId);
        if(account ==null){
            return null;
        }

        return accountDetailDao.getAccountDetails(account.getId(),page,pageSize);
    }
    public long getDayMoney(long userId,String day){
        return accountDetailDao.getDayMoney(userId,day);
    }

    public long getStepsDayMoney(long userId,String day){
        return accountDetailDao.getStepsDayMoney(userId,day);
    }

    //coinNum 支持正/负
    //如果为消费类型需要变成负值
    //发币专用因为发币详细日志中要把时间改为前一天
    public long coin(long userId,String channel,long coin) {

        if(coin == 0 ){
            return 0;
        }

        //判定用户是否而存在数据
        Account account = accountDao.getAccountByUserId(userId);

        if (coin < 0 && account != null && (coin + account.getBalance()) < 0) {
            return (coin + account.getBalance());
        }

        if (account == null) {
            account = saveAccount(userId, coin );
        } else {
            account = editAccount(account, coin);
        }
        //创建详细数据日志
        addTblAccountDetail(account.getId(), coin, account.getBalance(),channel);
        return 1;
    }



    private Account saveAccount(long userMainId, long coin) {
        Account account = new Account();

        account.setUserId(userMainId);
        account.setRewardBalance(coin );
        account.setRewardSum(coin );
        account.setBalance(coin );
        accountDao.add(account);
        return account;
    }


    private Account editAccount(Account account, long coin) {
        account.setRewardBalance(account.getRewardBalance() == null ? 0 : account.getRewardBalance() + coin );
        if (coin > 0) {
            account.setRewardSum(account.getRewardSum() == null ? 0 : account.getRewardSum() + coin );
        }
        account.setBalance(account.getBalance() == null ? 0 : account.getBalance() + coin );

        accountDao.update(account);
        return account;
    }


    private void addTblAccountDetail(long accountId, long rewardAmount, long blance, String channel) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        calendar.set(Calendar.MILLISECOND,0);
        AccountDetail accountDetail = new AccountDetail();
        accountDetail.setCreatedOn(calendar.getTime());
        accountDetail.setUpdatedOn(calendar.getTime());
        accountDetail.setUpdatedTimestamp(calendar.getTimeInMillis());
        accountDetail.setAccountId(accountId);
        accountDetail.setRewardAmount(rewardAmount );
        accountDetail.setBalance(blance);
        accountDetail.setChannel(channel);
       accountDetailDao.addAccountDetail(accountDetail);
    }

    //coinNum 支持正/负
    //如果为消费类型需要变成负值
    public long consumeCoin(long userId,String channel,long coin,Date createOn) {

        if(coin == 0 ){
            return 0;
        }

        //判定用户是否而存在数据
        Account account = accountDao.getAccountByUserId(userId);

        if (coin < 0 && account != null && (coin + account.getBalance()) < 0) {
            return (coin + account.getBalance());
        }

        if (account == null) {
            account = saveAccount(userId, coin );
        } else {
            account = editAccount(account, coin);
        }
        //创建详细数据日志
        addConsumeCoinDetail(account.getId(), coin, account.getBalance(),channel, createOn);
        return 1;
    }

    private void addConsumeCoinDetail(long accountId, long rewardAmount, long blance, String channel,Date createOn) {
        AccountDetail accountDetail = new AccountDetail();
        accountDetail.setAccountId(accountId);
        accountDetail.setRewardAmount(rewardAmount );
        accountDetail.setBalance(blance);
        accountDetail.setChannel(channel);
        accountDetail.setCreatedOn(createOn);
        accountDetail.setUpdatedOn(DateTime.now().toDate());
        accountDetail.setUpdatedTimestamp(DateTime.now().toDate().getTime());
        accountDetailDao.addAccountDetail(accountDetail);
    }

    public Account getAccountByUserId(Long userId){
        return accountMapper.getAccountByUserId(userId);
    }

    public List<AidocRankingVo> getAllAidocRanking(Map param){
        return accountMapper.getAllAidocRanking(param);
    }

    public int getAllAidocRankingCount(){
        return accountMapper.getAllAidocRankingCount();
    }

    public List<AidocRankingVo> getFriendlyAidocRanking(Map param){
        return accountMapper.getFriendlyAidocRanking(param);
    }

    public int getFriendlyAidocRankingCount(Map param){
        return accountMapper.getFriendlyAidocRankingCount(param);
    }
}
