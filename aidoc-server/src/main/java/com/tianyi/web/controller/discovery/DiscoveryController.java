/*
 * Copyright (c) 2018. Tianyi AIDOC Company.Inc. All Rights Reserved.
 */

package com.tianyi.web.controller.discovery;

import com.tianyi.bo.News;
import com.tianyi.bo.enums.LanguageEnum;
import com.tianyi.bo.enums.NewsEnum;
import com.tianyi.bo.match.SportMatch;
import com.tianyi.framework.bean.Page;
import com.tianyi.framework.bean.Pageable;
import com.tianyi.framework.controller.BaseController;
import com.tianyi.framework.util.Localize;
import com.tianyi.service.NewsService;
import com.tianyi.service.match.SportMatchService;
import com.tianyi.util.DateUtil;
import com.tianyi.web.controller.vo.ResultVO;
import com.tianyi.web.controller.vo.SportMatchVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * server
 *
 * @author Gray.Z
 * @date 2018/5/9 15:58.
 */
@RestController
@RequestMapping(value = "/discovery")
public class DiscoveryController extends BaseController {

    @Autowired
    private SportMatchService sportMatchService;
    @Resource
    private NewsService newsService;

    @GetMapping("/list")
    public ResponseEntity getDiscoveryList(@RequestParam(required = false) Integer pageNumber, @RequestParam(required = false) Integer pageSize) {
        if (pageNumber == null) {
            pageNumber = 1;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        LanguageEnum languageEnum = LanguageEnum.zh;
        if (Localize.getLocale() == Locale.US || Localize.getLocale() == Locale.UK) {
            languageEnum = LanguageEnum.en;
        }
        List<News> newList = newsService.getNews(NewsEnum.NEWS, pageNumber, pageSize, languageEnum, StringUtils.EMPTY);
        int total = newsService.getNewsTotleNum(NewsEnum.NEWS, languageEnum, StringUtils.EMPTY);

        List<Map<String, Object>> result = new ArrayList<>();
        for (News news : newList) {
            result.add(getMapFromNew(news));
        }

        Page<SportMatch> pages = sportMatchService.findInProgressByPage(new Pageable());

        List<SportMatchVO> match = pages.getContent().stream().map(getSportMatchVO()).collect(Collectors.toList());

        ResultVO resultVO = ResultVO.pageable(result, pageNumber, pageSize, total, total / pageSize + 1);
        resultVO.put("matches", match);
        return ResponseEntity.ok(resultVO);
    }

    private Function<SportMatch, SportMatchVO> getSportMatchVO() {
        return sportMatch -> {
            SportMatchVO sportMatchVO = new SportMatchVO();
            sportMatchVO.setTitle(sportMatch.getTitle());
            sportMatchVO.setCoverImageUrl(sportMatch.getCoverImageUrl());
            sportMatchVO.setStartDate(sportMatch.getStartDate());
            sportMatchVO.setEndDate(sportMatch.getEndDate());
            sportMatchVO.setEnterCount(sportMatch.getEnterCount());
            sportMatchVO.setTargetSteps(sportMatch.getTargetSteps());
            sportMatchVO.setStatus(
                sportMatch.getEndDate().compareTo(DateTime.now().toDate()) > 0 ? Localize.getMessage("sport_match_status_in_progress")
                    : Localize.getMessage("sport_match_status_finished"));
            return sportMatchVO;
        };
    }

    private Map<String, Object> getMapFromNew(News news) {
        Map<String, Object> result = new HashedMap();
        result.put("id", news.getId());
        result.put("title", news.getTitle());
        result.put("content", news.getContent());
        result.put("created_at", DateUtil.formatTime(news.getCreatedOn()));
        result.put("cover", news.getCover());
        result.put("watch_num", news.getWatchNum());
        result.put("forward_num", news.getForwardNum());
        result.put("lang", news.getLanguage().ordinal());
        result.put("summary", news.getSummary());
        result.put("thumbnail", news.getThumbnail());
        return result;
    }

}
