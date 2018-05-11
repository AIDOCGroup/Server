/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */
package com.aliyuncs.dysmsapi.transform.v20170525;

import com.aliyuncs.dysmsapi.model.v20170525.SendInterSmsResponse;
import com.aliyuncs.transform.UnmarshallerContext;


public class SendInterSmsResponseUnmarshaller {

	public static SendInterSmsResponse unmarshall(SendInterSmsResponse sendInterSmsResponse, UnmarshallerContext context) {
		
		sendInterSmsResponse.setRequestId(context.stringValue("SendInterSmsResponse.RequestId"));
		sendInterSmsResponse.setBizId(context.stringValue("SendInterSmsResponse.BizId"));
		sendInterSmsResponse.setCode(context.stringValue("SendInterSmsResponse.Code"));
		sendInterSmsResponse.setMessage(context.stringValue("SendInterSmsResponse.Message"));
	 
	 	return sendInterSmsResponse;
	}
}