/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */
package com.aliyuncs.dysmsapi.model.v20170525;

import java.util.List;
import com.aliyuncs.AcsResponse;
import com.aliyuncs.dysmsapi.transform.v20170525.QueryInterSmsIsoInfoResponseUnmarshaller;
import com.aliyuncs.transform.UnmarshallerContext;

/**
 * @author auto create
 * @version 
 */
public class QueryInterSmsIsoInfoResponse extends AcsResponse {

	private String requestId;

	private String code;

	private String message;

	private String totalCount;

	private List<IsoSupportDTO> isoSupportDTOs;

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

	public List<IsoSupportDTO> getIsoSupportDTOs() {
		return this.isoSupportDTOs;
	}

	public void setIsoSupportDTOs(List<IsoSupportDTO> isoSupportDTOs) {
		this.isoSupportDTOs = isoSupportDTOs;
	}

	public static class IsoSupportDTO {

		private String countryName;

		private String countryCode;

		private String isoCode;

		public String getCountryName() {
			return this.countryName;
		}

		public void setCountryName(String countryName) {
			this.countryName = countryName;
		}

		public String getCountryCode() {
			return this.countryCode;
		}

		public void setCountryCode(String countryCode) {
			this.countryCode = countryCode;
		}

		public String getIsoCode() {
			return this.isoCode;
		}

		public void setIsoCode(String isoCode) {
			this.isoCode = isoCode;
		}
	}

	@Override
	public QueryInterSmsIsoInfoResponse getInstance(UnmarshallerContext context) {
		return	QueryInterSmsIsoInfoResponseUnmarshaller.unmarshall(this, context);
	}
}
