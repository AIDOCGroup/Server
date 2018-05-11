/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */
package com.aliyuncs.dysmsapi.model.v20170525;

import com.aliyuncs.dysmsapi.transform.v20170525.QuerySendDetailsResponseUnmarshaller;
import java.util.List;
import com.aliyuncs.AcsResponse;
import com.aliyuncs.transform.UnmarshallerContext;

/**
 * @author auto create
 * @version 
 */
public class QuerySendDetailsResponse extends AcsResponse {

	private String requestId;

	private String code;

	private String message;

	private String totalCount;

	private List<SmsSendDetailDTO> smsSendDetailDTOs;

	public String getRequestId() {
		return this.requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
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

	public String getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public List<SmsSendDetailDTO> getSmsSendDetailDTOs() {
		return this.smsSendDetailDTOs;
	}

	public void setSmsSendDetailDTOs(List<SmsSendDetailDTO> smsSendDetailDTOs) {
		this.smsSendDetailDTOs = smsSendDetailDTOs;
	}

	public static class SmsSendDetailDTO {

		private String phoneNum;

		private Long sendStatus;

		private String errCode;

		private String templateCode;

		private String content;

		private String sendDate;

		private String receiveDate;

		private String outId;

		public String getPhoneNum() {
			return this.phoneNum;
		}

		public void setPhoneNum(String phoneNum) {
			this.phoneNum = phoneNum;
		}

		public Long getSendStatus() {
			return this.sendStatus;
		}

		public void setSendStatus(Long sendStatus) {
			this.sendStatus = sendStatus;
		}

		public String getErrCode() {
			return this.errCode;
		}

		public void setErrCode(String errCode) {
			this.errCode = errCode;
		}

		public String getTemplateCode() {
			return this.templateCode;
		}

		public void setTemplateCode(String templateCode) {
			this.templateCode = templateCode;
		}

		public String getContent() {
			return this.content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getSendDate() {
			return this.sendDate;
		}

		public void setSendDate(String sendDate) {
			this.sendDate = sendDate;
		}

		public String getReceiveDate() {
			return this.receiveDate;
		}

		public void setReceiveDate(String receiveDate) {
			this.receiveDate = receiveDate;
		}

		public String getOutId() {
			return this.outId;
		}

		public void setOutId(String outId) {
			this.outId = outId;
		}
	}

	@Override
	public QuerySendDetailsResponse getInstance(UnmarshallerContext context) {
		return	QuerySendDetailsResponseUnmarshaller.unmarshall(this, context);
	}
}
