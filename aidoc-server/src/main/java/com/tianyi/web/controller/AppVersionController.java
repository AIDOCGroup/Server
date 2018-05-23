package com.tianyi.web.controller;

import com.tianyi.bo.AppVersion;
import com.tianyi.bo.User;
import com.tianyi.framework.util.Localize;
import com.tianyi.service.AppVersionService;
import com.tianyi.service.I18nService;
import com.tianyi.util.DateUtil;
import com.tianyi.util.JsonPathArg;
import com.tianyi.web.AuthRequired;
import com.tianyi.web.model.ActionResult;
import com.tianyi.web.model.PagedListModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 雪峰 on 2017/10/17.
 */
@RestController
public class AppVersionController {
    @Autowired
    private AppVersionService appVersionService;
    @Autowired
    private I18nService i18nService;

    @RequestMapping(value = "/app/version", method = RequestMethod.GET)
    public Map<String, Object> valite_version(@RequestParam(value = "version") String version,
        @RequestParam(value = "versionType", required = false) String versionType,
        @RequestHeader("X-Client") String os) throws Exception {

        if ("1.0.0".equals(version) || "1.0.1".equals(version) || "1.0.2".equals(version) || "1.0.3"
            .equals(version)
            || "1.0.4".equals(version) || "1.0.5".equals(version) || "1.0.6".equals(version)
            || "1.0.7".equals(version)
            || "1.0.8".equals(version) || "1.0.9".equals(version) || "1.1.0".equals(version)) {
            version = "0.0.0";
        }
        List<AppVersion> appVersionList = appVersionService.getAppVersionList(version, os);

        Map<String, Object> result = new HashMap<>();
        if (appVersionList != null && !appVersionList.isEmpty()) {
            AppVersion appVersion = appVersionList.get(0);
            if (versionType == null || versionType.isEmpty()) {
                //IOS versionType 为空的话默认为个人
                if (StringUtils.isNotEmpty(os) && os.trim().equals("IOS")) {
                    appVersion = appVersionList.stream().filter(appVersion1 ->
                        appVersion1.getVersionType() == 0).findFirst()
                        .orElse(appVersion);
                }
            } else {
                appVersion = appVersionList.stream().filter(appVersion1 ->
                    appVersion1.getVersionType() == Integer.parseInt(versionType)).findFirst()
                    .orElse(appVersion);
            }
            result.put("version", appVersion.getVersion());
            result.put("downUrl", appVersion.getDownUrl());
            result.put("info", appVersion.getUpdateLog());
            result.put("size", appVersion.getTargetSize());
            result.put("isMust", appVersion.getIsMust());
            //分享地址
            result.put("pgyerUrl", appVersion.getPgyerUrl());
        }
        return result;
    }

  /**
   * 获取当前最新的版本信息,需求变更,此接口暂时用不到
   * @param os 操作平台,大小写不敏感 android,andorid,ios都可以
   * @return
   */
  @RequestMapping(value = "getRencentDownloadUrl")
  public AppVersion getRencentDownloadUrl(@RequestParam(value = "OS")String os) {
    AppVersion appVersion = appVersionService.getRencentDownloadUrl(os);
    return appVersion;
  }

    @AuthRequired
    @RequestMapping(value = "/admin/version/getVersionList", method = RequestMethod.GET)
    public PagedListModel<List<Map<String,Object>>> getUserAdministratorss(@RequestParam(value = "p", required = false) Integer page,
                                                                           @RequestParam(value = "p_size", required = false) Integer pageSize,
                                                                           @Value("#{request.getAttribute('currentUser')}") User currentUser) {
        page = page == null ? 0 : page;
        pageSize = pageSize == null ? 20 : pageSize;
        List<AppVersion> appVersions = appVersionService.getAppVersions(page, pageSize);
        int total = appVersionService.getTotalNumber();
        List<Map<String,Object>> result = new ArrayList<>();
        for (AppVersion appVersion : appVersions) {
            result.add(getMapFromAppVersion(appVersion));
        }
        return new PagedListModel(result, total, page, pageSize);
    }


    @AuthRequired
    @RequestMapping(value = "/admin/version/{id}", method = RequestMethod.GET)
    public Map<String,Object> getVersionById(@PathVariable("id") long id,@Value("#{request.getAttribute('lang')}") String lang) {
        AppVersion appVersion = appVersionService.getAppVersionById(id);

        if(appVersion == null){
            throw new RuntimeException("版本信息不存在");
        }

        return getMapFromAppVersion(appVersion);
    }


    @AuthRequired
    @RequestMapping(value = "/admin/version", method = RequestMethod.POST)
    public ActionResult addversion(@JsonPathArg("os") String os,
                                              @JsonPathArg("down_url") String down_url,
                                              @JsonPathArg("version") String version,
                                              @JsonPathArg("update_log") String update_log,
                                              @JsonPathArg("size") String size,
                                              @JsonPathArg("is_must") int is_must,
                                                @Value("#{request.getAttribute('lang')}") String lang,
                                              @Value("#{request.getAttribute('currentUser')}") User currentUser) {
        List<AppVersion> appVersionList = appVersionService.getAppVersionList(version, os);
        if(!appVersionList.isEmpty()){
            throw new RuntimeException(i18nService.getMessage("" + 113,lang));
        }


        AppVersion appVersion = new AppVersion();
        appVersion.setDownUrl(down_url);
        appVersion.setIsMust(is_must);
        appVersion.setMobileOs(os);
        appVersion.setTargetSize(size);
        appVersion.setUpdateLog(update_log);
        appVersion.setVersion(version);
        if ("ANDORID".equals(os)) {
            appVersion.setPgyerUrl("https://www.pgyer.com/JaIZ");
        }

        appVersionService.addAppVersion(appVersion);




        return new ActionResult();
    }



    @AuthRequired
    @RequestMapping(value = "/admin/version/{id}", method = RequestMethod.PUT)
    public ActionResult editVersion(@PathVariable("id") long id,@JsonPathArg("os") String os,
                                              @JsonPathArg("down_url") String down_url,
                                              @JsonPathArg("version") String version,
                                              @JsonPathArg("update_log") String update_log,
                                              @JsonPathArg("size") String size,
                                              @JsonPathArg("is_must") int is_must,
                                                @Value("#{request.getAttribute('lang')}") String lang,
                                              @Value("#{request.getAttribute('currentUser')}") User currentUser) {
        AppVersion appVersion = appVersionService.getAppVersionById(id);
        if(appVersion == null){
            throw new RuntimeException(i18nService.getMessage("" + 113,lang));
        }

        appVersion.setDownUrl(down_url);
        appVersion.setIsMust(is_must);
        appVersion.setMobileOs(os);
        appVersion.setTargetSize(size);
        appVersion.setUpdateLog(update_log);
        appVersion.setVersion(version);

        appVersionService.editAppVersion(appVersion);
        ActionResult result = new ActionResult();
        result.setErr_code(0);
        result.setErr_msg(Localize.getMessage("common.message.success"));
        return result;
    }


    @AuthRequired
    @RequestMapping(value = "/admin/version/{id}", method = RequestMethod.DELETE)
    public ActionResult delVersion(@PathVariable("id") long id,@Value("#{request.getAttribute('lang')}") String lang) {
        AppVersion appVersion = appVersionService.getAppVersionById(id);
        if(appVersion == null){
            throw new RuntimeException(i18nService.getMessage("" + 113,lang));
        }

        appVersionService.delAppVersion(appVersion);
        ActionResult result = new ActionResult();
        result.setErr_code(0);
        result.setErr_msg(Localize.getMessage("common.message.success"));
        return result;
    }



    private Map<String,Object> getMapFromAppVersion(AppVersion appVersion){
        Map<String,Object> result = new HashMap<>();
        result.put("id",appVersion.getId());
        result.put("created_on", DateUtil.formatTime(appVersion.getCreatedOn()));
        result.put("os",appVersion.getMobileOs());
        result.put("down_url",appVersion.getDownUrl());
        result.put("version",appVersion.getVersion());
        result.put("update_log",appVersion.getUpdateLog());
        result.put("size",appVersion.getTargetSize());
        result.put("is_must",appVersion.getIsMust());


        return result;
    }

}
