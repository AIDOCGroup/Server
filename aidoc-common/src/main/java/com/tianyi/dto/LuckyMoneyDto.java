package com.tianyi.dto;

public class LuckyMoneyDto {
    private String totalAmount;
    private String totalNum;
    private String tradePassWord;
    private String comments;
    private String type;

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public String getTradePassWord() {
        return tradePassWord;
    }

    public void setTradePassWord(String tradePassWord) {
        this.tradePassWord = tradePassWord;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
