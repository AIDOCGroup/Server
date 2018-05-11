package com.tianyi.bo;

import com.tianyi.bo.base.BaseBo;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by 雪峰 on 2018/1/16.
 */
@Entity
@DynamicUpdate
@DynamicInsert
public class AidocSet extends BaseBo implements Serializable {

    /**
     * 发放金额
     */
    private int aidocTotal;
  /**
   * 新增字段setValue,对应setType,保留原有aidoctotal保持兼容
   */
  private String setValue;
    /**
     * 生效时间
     */
    @DateTimeFormat(pattern = "'yyyy-MM-dd'T'HH:mm:ss.SSSZ'")
    private Date effectiveDate;
    /**
     * 失效时间
     */
    @DateTimeFormat(pattern = "'yyyy-MM-dd'T'HH:mm:ss.SSSZ'")
    private Date invalidDate;
    /**
     * 类别
     * 0：默认发放金额
     * 1：设置金额
     */
    private int setType;
    /**
     * 类别
     * 0：无效
     * 1：有效
     */
    private int status;

    /**
     *
     */
    private long  operationUserId;

    private String setName;

    private Integer personalLimit;


    public int getAidocTotal() {
        return aidocTotal;
    }

    public void setAidocTotal(int aidocTotal) {
        this.aidocTotal = aidocTotal;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getInvalidDate() {
        return invalidDate;
    }

    public void setInvalidDate(Date invalidDate) {
        this.invalidDate = invalidDate;
    }


    public int getSetType() {
        return setType;
    }

    public void setSetType(int setType) {
        this.setType = setType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getOperationUserId() {
        return operationUserId;
    }

    public void setOperationUserId(long operationUserId) {
        this.operationUserId = operationUserId;
    }

  public String getSetValue() {
    return setValue;
  }

  public void setSetValue(String setValue) {
    this.setValue = setValue;
  }

  public String getSetName() {
    return setName;
  }

  public void setSetName(String setName) {
    this.setName = setName;
  }

  public Integer getPersonalLimit() {
    return personalLimit;
  }

  public void setPersonalLimit(Integer personalLimit) {
    this.personalLimit = personalLimit;
  }

  @Override
  public String toString() {
    return "AidocSet{" +
            "aidocTotal=" + aidocTotal +
            ", setValue='" + setValue + '\'' +
            ", effectiveDate=" + effectiveDate +
            ", invalidDate=" + invalidDate +
            ", setType=" + setType +
            ", status=" + status +
            ", operationUserId=" + operationUserId +
            ", setName='" + setName + '\'' +
            ", personalLimit=" + personalLimit +
            ", id=" + id +
            ", createdOn=" + createdOn +
            ", updatedOn=" + updatedOn +
            '}';
  }
}
