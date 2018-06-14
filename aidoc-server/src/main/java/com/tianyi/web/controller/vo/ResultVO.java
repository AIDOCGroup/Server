/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.web.controller.vo;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * server
 *
 * @author Gray.Z
 * @date 2018/5/7 16:39.
 */
public class ResultVO extends LinkedHashMap<String, Object> {

    public static ResultVO list(List data) {
        ResultVO rv = new ResultVO();
        rv.put("data", data);
        return rv;
    }

    public static ResultVO pageable(List data, int pageNumber, int pageSize, long total, int totalPages) {
        ResultVO rv = new ResultVO();
        rv.put("data", data);
        rv.put("pageNumber", pageNumber);
        rv.put("pageSize", pageSize);
        rv.put("total", total);
        rv.put("totalPages", totalPages);
        return rv;
    }
}
