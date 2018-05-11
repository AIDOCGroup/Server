/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.bo;

import com.tianyi.framework.entity.BaseEntity;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户行为日志
 *
 * @author Gray.Z
 * @date 2018/4/18 19:46.
 */
@Entity
@Table(name = "user_behavior_log", schema = "aidoc")
public class UserBehaviorLog extends BaseEntity<Long> {

    private Long userId;
    private String requestMethod;
    private String requestUrl;
    private String sourceIp;
    private String clientType;
    private String language;
    private String appVersion;
    private String requestParam;
    private String responseResult;

    @Basic
    @Column(name = "user_id", nullable = true)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "request_method", nullable = true, length = 10)
    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    @Basic
    @Column(name = "request_url", nullable = true, length = 255)
    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    @Basic
    @Column(name = "source_ip", nullable = true, length = 64)
    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    @Basic
    @Column(name = "client_type", nullable = true, length = 10)
    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    @Basic
    @Column(name = "language", nullable = true, length = 10)
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Basic
    @Column(name = "app_version", nullable = true, length = 32)
    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    @Basic
    @Column(name = "request_param", nullable = true, length = 500)
    public String getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }

    @Basic
    @Column(name = "response_result", nullable = true, length = 5000)
    public String getResponseResult() {
        return responseResult;
    }

    public void setResponseResult(String responseResult) {
        this.responseResult = responseResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        UserBehaviorLog that = (UserBehaviorLog) o;

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) {
            return false;
        }
        if (requestMethod != null ? !requestMethod.equals(that.requestMethod) : that.requestMethod != null) {
            return false;
        }
        if (requestUrl != null ? !requestUrl.equals(that.requestUrl) : that.requestUrl != null) {
            return false;
        }
        if (sourceIp != null ? !sourceIp.equals(that.sourceIp) : that.sourceIp != null) {
            return false;
        }
        if (clientType != null ? !clientType.equals(that.clientType) : that.clientType != null) {
            return false;
        }
        if (language != null ? !language.equals(that.language) : that.language != null) {
            return false;
        }
        if (appVersion != null ? !appVersion.equals(that.appVersion) : that.appVersion != null) {
            return false;
        }
        if (requestParam != null ? !requestParam.equals(that.requestParam) : that.requestParam != null) {
            return false;
        }
        return responseResult != null ? responseResult.equals(that.responseResult) : that.responseResult == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (requestMethod != null ? requestMethod.hashCode() : 0);
        result = 31 * result + (requestUrl != null ? requestUrl.hashCode() : 0);
        result = 31 * result + (sourceIp != null ? sourceIp.hashCode() : 0);
        result = 31 * result + (clientType != null ? clientType.hashCode() : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (appVersion != null ? appVersion.hashCode() : 0);
        result = 31 * result + (requestParam != null ? requestParam.hashCode() : 0);
        result = 31 * result + (responseResult != null ? responseResult.hashCode() : 0);
        return result;
    }
}
