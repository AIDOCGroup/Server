package com.tianyi.vo;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * server
 *
 * @author GaoZhilai
 * @date 2018/4/23.
 */
public class AidocMarketQuotationVo {
  private Long id;
  private Integer page;
  private Integer size;
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date start_time;
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date end_time;
  private Double price_avg;
  private Date quote_date;
  private Long operation_user_id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  public Date getStart_time() {
    return start_time;
  }

  public void setStart_time(Date start_time) {
    this.start_time = start_time;
  }

  public Date getEnd_time() {
    return end_time;
  }

  public void setEnd_time(Date end_time) {
    this.end_time = end_time;
  }

  public Double getPrice_avg() {
    return price_avg;
  }

  public void setPrice_avg(Double price_avg) {
    this.price_avg = price_avg;
  }

  public Date getQuote_date() {
    return quote_date;
  }

  public void setQuote_date(Date quote_date) {
    this.quote_date = quote_date;
  }

  public Long getOperation_user_id() {
    return operation_user_id;
  }

  public void setOperation_user_id(Long operation_user_id) {
    this.operation_user_id = operation_user_id;
  }
}
