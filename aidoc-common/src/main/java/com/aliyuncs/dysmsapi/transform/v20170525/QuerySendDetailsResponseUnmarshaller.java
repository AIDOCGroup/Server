/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */
package com.aliyuncs.dysmsapi.transform.v20170525;

import java.util.ArrayList;
import java.util.List;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse.SmsSendDetailDTO;
import com.aliyuncs.transform.UnmarshallerContext;


public class QuerySendDetailsResponseUnmarshaller {

	public static QuerySendDetailsResponse unmarshall(QuerySendDetailsResponse querySendDetailsResponse, UnmarshallerContext context) {
		
		querySendDetailsResponse.setRequestId(context.stringValue("QuerySendDetailsResponse.RequestId"));
		querySendDetailsResponse.setCode(context.stringValue("QuerySendDetailsResponse.Code"));
		querySendDetailsResponse.setMessage(context.stringValue("QuerySendDetailsResponse.Message"));
		querySendDetailsResponse.setTotalCount(context.stringValue("QuerySendDetailsResponse.TotalCount"));

		List<SmsSendDetailDTO> smsSendDetailDTOs = new ArrayList<SmsSendDetailDTO>();
		for (int i = 0; i < context.lengthValue("QuerySendDetailsResponse.SmsSendDetailDTOs.Length"); i++) {
			SmsSendDetailDTO smsSendDetailDTO = new SmsSendDetailDTO();
			smsSendDetailDTO.setPhoneNum(context.stringValue("QuerySendDetailsResponse.SmsSendDetailDTOs["+ i +"].PhoneNum"));
			smsSendDetailDTO.setSendStatus(context.longValue("QuerySendDetailsResponse.SmsSendDetailDTOs["+ i +"].SendStatus"));
			smsSendDetailDTO.setErrCode(context.stringValue("QuerySendDetailsResponse.SmsSendDetailDTOs["+ i +"].ErrCode"));
			smsSendDetailDTO.setTemplateCode(context.stringValue("QuerySendDetailsResponse.SmsSendDetailDTOs["+ i +"].TemplateCode"));
			smsSendDetailDTO.setContent(context.stringValue("QuerySendDetailsResponse.SmsSendDetailDTOs["+ i +"].Content"));
			smsSendDetailDTO.setSendDate(context.stringValue("QuerySendDetailsResponse.SmsSendDetailDTOs["+ i +"].SendDate"));
			smsSendDetailDTO.setReceiveDate(context.stringValue("QuerySendDetailsResponse.SmsSendDetailDTOs["+ i +"].ReceiveDate"));
			smsSendDetailDTO.setOutId(context.stringValue("QuerySendDetailsResponse.SmsSendDetailDTOs["+ i +"].OutId"));

			smsSendDetailDTOs.add(smsSendDetailDTO);
		}
		querySendDetailsResponse.setSmsSendDetailDTOs(smsSendDetailDTOs);
	 
	 	return querySendDetailsResponse;
	}
}