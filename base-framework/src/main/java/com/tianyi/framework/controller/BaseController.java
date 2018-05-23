/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.framework.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * server
 *
 * @author Gray.Z
 * @date 2018/4/21 13:43.
 */
public class BaseController {

    protected final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * 错误消息
     */
    protected static final String ERROR_MESSAGE = "common.message.error";

    /**
     * 成功消息
     */
    protected static final String SUCCESS_MESSAGE = "common.message.success";

}
