/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.web.controller.mine;

import com.tianyi.bo.MiningSecretCard;
import com.tianyi.framework.bean.Page;
import com.tianyi.framework.bean.Pageable;
import com.tianyi.framework.controller.BaseController;
import com.tianyi.framework.util.Constants;
import com.tianyi.framework.util.JSONUtils;
import com.tianyi.framework.util.Localize;
import com.tianyi.service.mine.MiningSecretCardService;
import com.tianyi.web.controller.vo.BaseVO;
import com.tianyi.web.model.ResponseResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 挖矿卡
 *
 * @author Gray.Z
 * @date 2018/4/11 15:33.
 */
@RestController
@RequestMapping(value = "/mining/secret_card")
public class MiningSecretCardController extends BaseController {

    @Autowired
    private MiningSecretCardService miningSecretCardService;


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getSecretCard(@PathVariable Long id) {
        return ResponseEntity.ok(miningSecretCardService.getMiningSecretCard(id));
    }

    /**
     * 获取卡密数据列表
     *
     * @param pageable 分页以及查询参数封装
     * @return ResponseEntity
     */
    @RequestMapping(value = "/list")
    public ResponseEntity getSecretCards(@RequestBody(required = false) Pageable pageable) {
        Page<MiningSecretCard> page;
        try {
            if (pageable == null) {
                pageable = new Pageable();
            }
            page = miningSecretCardService.findAll(pageable);
            logger.info("page info data:{}", JSONUtils.writeValueAsString(page));
        } catch (Exception e) {
            logger.error("get mining secret cards error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(page);
    }

    /**
     * 保存卡密数据
     *
     * @param miningSecretCard 卡密对象
     * @return ResponseEntity
     */
    @PostMapping
    public ResponseEntity saveSecretCard(@RequestBody MiningSecretCard miningSecretCard) {
        try {
            if (miningSecretCard.getUserId() == null || StringUtils
                .isEmpty(miningSecretCard.getUsername())) {
                return ResponseEntity.ok(Localize.getMessage("mining_message_user_not_empty"));
            }
            miningSecretCardService.save(miningSecretCard);
        } catch (Exception e) {
            logger.error("save mining secret cards error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseResult.OK;
    }

    /**
     * 修改卡密数据-订单号，状态等
     *
     * @param miningSecretCard 卡密对象
     * @return ResponseEntity
     */
    @PutMapping
    public ResponseEntity modifySecretCard(@RequestBody MiningSecretCard miningSecretCard) {
        try {
            if (miningSecretCard.getId() == null) {
                return ResponseEntity.ok("id is null,update failed!");
            }
            miningSecretCardService.update(miningSecretCard);
        } catch (Exception e) {
            logger.error("modify mining secret cards error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseResult.OK;
    }

    /**
     * 删除卡密数据
     *
     * @param id 卡密ID
     * @return ResponseEntity
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity removeSecretCard(@PathVariable Long id) {
        try {
            miningSecretCardService.remove(id);
        } catch (Exception e) {
            logger.error("remove mining secret cards error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseResult.OK;
    }

    @RequestMapping(value = "/get_my_detail/{id}", method = RequestMethod.GET)
    public ResponseEntity getMySecretCardDetail(@PathVariable Long id) {
        List<MiningSecretCard> tempResult = miningSecretCardService.getUserMiningSecretCards(id);
        Map resultMap = new HashMap();
        List<BaseVO> result = new ArrayList<>();
        for (MiningSecretCard ele : tempResult) {
            BaseVO temp = new BaseVO();

            String validTime;
            String description = ele.getPrice() + Localize.getMessage("mining_message_price_description");
            if (new DateTime(ele.getActiveDate()).toString(Constants.DATE_PATTERN)
                .equals(DateTime.now().plusDays(1).toString(Constants.DATE_PATTERN))) {
                validTime = Localize.getMessage("mining_message_invalid_default_description");
            } else {
                validTime = Localize.getMessage("mining_message_invalid_description",
                    (ele.getInvalidDate().getTime() - System.currentTimeMillis()) / 1000 / 60 / 60 + "");
            }

            temp.setName(description);
            temp.setValue(validTime);
            result.add(temp);
        }
        resultMap.put("status", "ok");
        resultMap.put("msg", "查询成功");
        resultMap.put("content", result);
        return ResponseEntity.ok(resultMap);
    }
}
