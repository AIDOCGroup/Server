/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */
package com.aliyuncs.dysmsapi.transform.v20170525;

import com.aliyuncs.dysmsapi.model.v20170525.QueryInterSmsIsoInfoResponse;
import com.aliyuncs.dysmsapi.model.v20170525.QueryInterSmsIsoInfoResponse.IsoSupportDTO;
import java.util.ArrayList;
import java.util.List;

import com.aliyuncs.transform.UnmarshallerContext;


public class QueryInterSmsIsoInfoResponseUnmarshaller {

	public static QueryInterSmsIsoInfoResponse unmarshall(QueryInterSmsIsoInfoResponse queryInterSmsIsoInfoResponse, UnmarshallerContext context) {
		
		queryInterSmsIsoInfoResponse.setRequestId(context.stringValue("QueryInterSmsIsoInfoResponse.RequestId"));
		queryInterSmsIsoInfoResponse.setCode(context.stringValue("QueryInterSmsIsoInfoResponse.Code"));
		queryInterSmsIsoInfoResponse.setMessage(context.stringValue("QueryInterSmsIsoInfoResponse.Message"));
		queryInterSmsIsoInfoResponse.setTotalCount(context.stringValue("QueryInterSmsIsoInfoResponse.TotalCount"));

		List<IsoSupportDTO> isoSupportDTOs = new ArrayList<IsoSupportDTO>();
		for (int i = 0; i < context.lengthValue("QueryInterSmsIsoInfoResponse.IsoSupportDTOs.Length"); i++) {
			IsoSupportDTO isoSupportDTO = new IsoSupportDTO();
			isoSupportDTO.setCountryName(context.stringValue("QueryInterSmsIsoInfoResponse.IsoSupportDTOs["+ i +"].CountryName"));
			isoSupportDTO.setCountryCode(context.stringValue("QueryInterSmsIsoInfoResponse.IsoSupportDTOs["+ i +"].CountryCode"));
			isoSupportDTO.setIsoCode(context.stringValue("QueryInterSmsIsoInfoResponse.IsoSupportDTOs["+ i +"].IsoCode"));

			isoSupportDTOs.add(isoSupportDTO);
		}
		queryInterSmsIsoInfoResponse.setIsoSupportDTOs(isoSupportDTOs);
	 
	 	return queryInterSmsIsoInfoResponse;
	}
}