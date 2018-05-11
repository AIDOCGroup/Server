package com.tianyi.vo;

/**
 * author:CDH
 * Date:2018/5/9
 */
public class TrackOrderVo {

    private String odd_num;
    private String uid;
    private String create_time;
    private String odd_status;
    private String coin_num;
    private String eth_address;
    private String hash;
    private String hash_status;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getHash_status() {
        return hash_status;
    }

    public void setHash_status(String hash_status) {
        this.hash_status = hash_status;
    }

    public String getOdd_num() {
        return odd_num;
    }

    public void setOdd_num(String odd_num) {
        this.odd_num = odd_num;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getOdd_status() {
        return odd_status;
    }

    public void setOdd_status(String odd_status) {
        this.odd_status = odd_status;
    }

    public String getCoin_num() {
        return coin_num;
    }

    public void setCoin_num(String coin_num) {
        this.coin_num = coin_num;
    }

    public String getEth_address() {
        return eth_address;
    }

    public void setEth_address(String eth_address) {
        this.eth_address = eth_address;
    }
}
