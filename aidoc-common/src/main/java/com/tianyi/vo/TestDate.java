package com.tianyi.vo;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * server
 *
 * @author GaoZhilai
 * @date 2018/4/18.
 */
public class TestDate {
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date month;

  public Date getMonth() {
    return month;
  }

  public void setMonth(Date month) {
    this.month = month;
  }
}
