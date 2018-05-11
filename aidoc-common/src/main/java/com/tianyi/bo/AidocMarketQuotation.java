package com.tianyi.bo;

import java.util.Date;

/**
 * server
 *
 * @author GaoZhilai
 * @date 2018/4/20.
 */
public class AidocMarketQuotation {
  private Long id;
  private Date createdOn;
  private Date updatedOn;
  private Long updatedTimestamp;
  private Double priceAvg;
  private Long operationUserId;
  private Date quoteDate;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(Date createdOn) {
    this.createdOn = createdOn;
  }

  public Date getUpdatedOn() {
    return updatedOn;
  }

  public void setUpdatedOn(Date updatedOn) {
    this.updatedOn = updatedOn;
  }

  public Long getUpdatedTimestamp() {
    return updatedTimestamp;
  }

  public void setUpdatedTimestamp(Long updatedTimestamp) {
    this.updatedTimestamp = updatedTimestamp;
  }

  public Double getPriceAvg() {
    return priceAvg;
  }

  public void setPriceAvg(Double priceAvg) {
    this.priceAvg = priceAvg;
  }

  public Long getOperationUserId() {
    return operationUserId;
  }

  public void setOperationUserId(Long operationUserId) {
    this.operationUserId = operationUserId;
  }

  public Date getQuoteDate() {
    return quoteDate;
  }

  public void setQuoteDate(Date quoteDate) {
    this.quoteDate = quoteDate;
  }
}
