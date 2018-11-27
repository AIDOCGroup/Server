package me.aidoc.appserver.po;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author vliu
 * @create 2018-10-09 15:33
 **/
@Data
public class AccountToken extends BasePo{

    /**
     * 该条记录所属者的ID
     */
    private Long userId;
    /**
     * AIDX的数量
     */
    private BigDecimal aidxBalance;

    /**
     * AIDH的数量
     */
    private BigDecimal aidhBalance;
    /**
     * 是否逻辑删除的标志
     */
    private Integer isDelete;

    /**
     * 是否激活
     */
    private Integer isActive;
}
