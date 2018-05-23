package com.tianyi.vo;

/**
 * author:CDH
 * Date:2018/5/17
 */
public class JudgmentPasswordVo {

    /**
     * 0成功，1三小时内，2三小时后
     */
    private Integer type;
    /**
     * 剩余次数
     */
    private Integer num;
    private int err_code;
    private String err_msg;

    public int getErr_code() {
        return err_code;
    }

    public void setErr_code(int err_code) {
        this.err_code = err_code;
    }

    public String getErr_msg() {
        return err_msg;
    }

    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
