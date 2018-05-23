package com.tianyi.web.model;

/**
 * Created by 雪峰 on 2018/1/1.
 */
public class ActionResult {
    private int err_code;
    private String err_msg;

    public ActionResult() {
    }

    public ActionResult(int err_code, String err_msg) {
        this.err_code = err_code;
        this.err_msg = err_msg;
    }

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
}
