/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */
package com.aliyuncs.dysmsapi.transform.v20170525;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.transform.UnmarshallerContext;


public class SendSmsResponseUnmarshaller {

	public static SendSmsResponse unmarshall(SendSmsResponse sendSmsResponse, UnmarshallerContext context) {
		
		sendSmsResponse.setRequestId(context.stringValue("SendSmsResponse.RequestId"));
		sendSmsResponse.setBizId(context.stringValue("SendSmsResponse.BizId"));
		sendSmsResponse.setCode(context.stringValue("SendSmsResponse.Code"));
		sendSmsResponse.setMessage(context.stringValue("SendSmsResponse.Message"));
	 
	 	return sendSmsResponse;
	}
}