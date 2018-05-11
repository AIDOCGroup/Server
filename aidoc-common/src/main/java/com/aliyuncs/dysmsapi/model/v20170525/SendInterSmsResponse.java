/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */
package com.aliyuncs.dysmsapi.model.v20170525;

import com.aliyuncs.AcsResponse;
import com.aliyuncs.dysmsapi.transform.v20170525.SendInterSmsResponseUnmarshaller;
import com.aliyuncs.transform.UnmarshallerContext;

/**
 * @author auto create
 * @version 
 */
public class SendInterSmsResponse extends AcsResponse {

	private String requestId;

	private String bizId;

	private String code;

	private String message;

	public String getRequestId() {
		return this.requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getBizId() {
		return this.bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public SendInterSmsResponse getInstance(UnmarshallerContext context) {
		return	SendInterSmsResponseUnmarshaller.unmarshall(this, context);
	}
}
