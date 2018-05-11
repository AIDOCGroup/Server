/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */
package com.aliyuncs.dysmsapi.model.v20170525;

import com.aliyuncs.RpcAcsRequest;

/**
 * @author auto create
 * @version 
 */
public class QueryInterSmsIsoInfoRequest extends RpcAcsRequest<QueryInterSmsIsoInfoResponse> {
	
	public QueryInterSmsIsoInfoRequest() {
		super("Dysmsapi", "2017-05-25", "QueryInterSmsIsoInfo");
	}

	private String resourceOwnerAccount;

	private String countryName;

	private Long resourceOwnerId;

	private Long ownerId;

	public String getResourceOwnerAccount() {
		return this.resourceOwnerAccount;
	}

	public void setResourceOwnerAccount(String resourceOwnerAccount) {
		this.resourceOwnerAccount = resourceOwnerAccount;
		if(resourceOwnerAccount != null){
			putQueryParameter("ResourceOwnerAccount", resourceOwnerAccount);
		}
	}

	public String getCountryName() {
		return this.countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
		if(countryName != null){
			putQueryParameter("CountryName", countryName);
		}
	}

	public Long getResourceOwnerId() {
		return this.resourceOwnerId;
	}

	public void setResourceOwnerId(Long resourceOwnerId) {
		this.resourceOwnerId = resourceOwnerId;
		if(resourceOwnerId != null){
			putQueryParameter("ResourceOwnerId", resourceOwnerId.toString());
		}
	}

	public Long getOwnerId() {
		return this.ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
		if(ownerId != null){
			putQueryParameter("OwnerId", ownerId.toString());
		}
	}

	@Override
	public Class<QueryInterSmsIsoInfoResponse> getResponseClass() {
		return QueryInterSmsIsoInfoResponse.class;
	}

}
