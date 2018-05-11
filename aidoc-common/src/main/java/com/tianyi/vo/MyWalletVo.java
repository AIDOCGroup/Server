package com.tianyi.vo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * server
 *
 * @author GaoZhilai
 * @date 2018/4/17.
 */
public class MyWalletVo {
  private Integer page;
  private Integer size;
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SS")
  private Date month;

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  public Date getMonth() {
    return month;
  }

  public void setMonth(Date month) {
    this.month = month;
  }
}
