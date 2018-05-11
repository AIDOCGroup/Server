package com.tianyi.bo;

import com.tianyi.bo.enums.AccountChannelEnum;

import java.util.Date;

/**
 * server
 *
 * @author GaoZhilai
 * @date 2018/4/17.
 */
public class WalletDetailEle {
  private String description;
  private long time;
  private String money;
  private int typeCode;
  private String mark;

  /**
   * 交易方向-0:收,1:付
   */
  private Integer transDir;

  public String getMark() {
    return mark;
  }

  public void setMark(String mark) {
    this.mark = mark;
  }

  public Integer getTransDir() {
    return transDir;
  }

  public void setTransDir(Integer transDir) {
    this.transDir = transDir;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public String getMoney() {
    return money;
  }

  public void setMoney(String money) {
    this.money = money;
  }

  public int getTypeCode() {
    return typeCode;
  }

  public void setTypeCode(int typeCode) {
    this.typeCode = typeCode;
  }
}
