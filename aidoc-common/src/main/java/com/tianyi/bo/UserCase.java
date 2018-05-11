package com.tianyi.bo;

import com.tianyi.bo.base.BaseBo;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 雪峰 on 2018/1/1.
 */
@Entity
@DynamicUpdate
@DynamicInsert
public class UserCase extends BaseBo implements Serializable {
    private long userId;
    //就诊时间
    private Date visitDay;
    //就诊类型0:门诊，1：急诊，2：住院，3取消
    private int visitType;
    //就诊医院
    private String visitHospial;
    //就诊科室
    private String visitDepartment;
    //医生名称
    private String doctorName;
    //就诊费用
    private float visitFee;
    //就诊原因
    private String visitReason;
    //就诊结果
    private String visitResult;
    //医生建议
    private String doctorAdvice;
    //用药与处方
    private String doctorInfo;
    //检查报告
    private String visitRepost;

    //病例原件
    private String casePhoto;

    //逻辑删除 1 可用 0 删除
    @Column(nullable=true,columnDefinition="INT default 1")
    private int caseStatus;


    public int getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(int caseStatus) {
        this.caseStatus = caseStatus;
    }



    private String visitReasonUrls;
    public String getVisitReasonUrls() {
        return visitReasonUrls;
    }
    public void setVisitReasonUrls(String visitReasonUrls) {
        this.visitReasonUrls = visitReasonUrls;
    }

    private String visitResultUrls;
    public String getVisitResultUrls() {
        return visitResultUrls;
    }
    public void setVisitResultUrls(String visitResultUrls) {
        this.visitResultUrls = visitResultUrls;
    }

    private String doctorAdviceUrls;
    public String getDoctorAdviceUrls() {
        return doctorAdviceUrls;
    }
    public void setDoctorAdviceUrls(String doctorAdviceUrls) {
        this.doctorAdviceUrls = doctorAdviceUrls;
    }

    private String doctorInfoUrls;
    public String getDoctorInfoUrls() {
        return doctorInfoUrls;
    }
    public void setDoctorInfoUrls(String doctorInfoUrls) {
        this.doctorInfoUrls = doctorInfoUrls;
    }

    private String visitRepostUrls;
    public String getVisitRepostUrls() {
        return visitRepostUrls;
    }
    public void setVisitRepostUrls(String visitRepostUrls) {
        this.visitRepostUrls = visitRepostUrls;
    }

    private String casePhotoUrls;
    public String getCasePhotoUrls() {
        return casePhotoUrls;
    }
    public void setCasePhotoUrls(String casePhotoUrls) {
        this.casePhotoUrls = casePhotoUrls;
    }

    public List<String> getVisitReasonImg() {

        return getUrls(visitReasonUrls);
    }
    public List<String> getVisitResultImg() {

        return getUrls(visitResultUrls);
    }
    public List<String> getDoctorAdviceImg() {

        return getUrls(doctorAdviceUrls);
    }
    public List<String> getDoctorInfoImg() {

        return getUrls(doctorInfoUrls);
    }
    public List<String> getVisitRepostImg() {

        return getUrls(visitRepostUrls);
    }
    public List<String> getCasePhotoImg() {

        return getUrls(casePhotoUrls);
    }

    private List<String> getUrls(String key) {
        try {
            List<String> urlList = new ArrayList<>();

            if(key==null || "".equals(key) || key.length()<=0){
                return new ArrayList<String>();
            }

            String[] urls = key.split(",");
            for(String url :urls){
                urlList.add(url);
            }
            return urlList;
        } catch (Exception ex) {
            return new ArrayList<String>();
        }
    }


    /**
     * 附件文件key,JSON数组格式
     */
    private String attachFileKeyJson;
    @Transient
    private List<String> attachFileKeys;


    public void setAttachFileKeyJson(String attachFileKeyJson) {
        this.attachFileKeyJson = attachFileKeyJson;
    }

    public List<String> getAttachFileKeys() {
        if (attachFileKeys != null && attachFileKeys.size() > 0) {
            return attachFileKeys;
        }
        attachFileKeys = new ArrayList<String>();

        if (StringUtils.isNotBlank(attachFileKeyJson)) {
            attachFileKeys.addAll(JSON.parseArray(attachFileKeyJson, String.class));
        }

        return attachFileKeys;
    }


    public void setCasePhoto(String casePhoto) {
        this.casePhoto = casePhoto;
    }

    public String getCasePhoto() {
        return casePhoto;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getVisitDay() {
        return visitDay;
    }

    public void setVisitDay(Date visitDay) {
        this.visitDay = visitDay;
    }

    public int getVisitType() {
        return visitType;
    }

    public void setVisitType(int visitType) {
        this.visitType = visitType;
    }

    public String getVisitHospial() {
        return visitHospial;
    }

    public void setVisitHospial(String visitHospial) {
        this.visitHospial = visitHospial;
    }

    public String getVisitDepartment() {
        return visitDepartment;
    }

    public void setVisitDepartment(String visitDepartment) {
        this.visitDepartment = visitDepartment;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public float getVisitFee() {
        return visitFee;
    }

    public void setVisitFee(float visitFee) {
        this.visitFee = visitFee;
    }

    public String getVisitReason() {
        return visitReason;
    }

    public void setVisitReason(String visitReason) {
        this.visitReason = visitReason;
    }

    public String getVisitResult() {
        return visitResult;
    }

    public void setVisitResult(String visitResult) {
        this.visitResult = visitResult;
    }

    public String getDoctorAdvice() {
        return doctorAdvice;
    }

    public void setDoctorAdvice(String doctorAdvice) {
        this.doctorAdvice = doctorAdvice;
    }

    public String getDoctorInfo() {
        return doctorInfo;
    }

    public void setDoctorInfo(String doctorInfo) {
        this.doctorInfo = doctorInfo;
    }

    public String getVisitRepost() {
        return visitRepost;
    }

    public void setVisitRepost(String visitRepost) {
        this.visitRepost = visitRepost;
    }


}