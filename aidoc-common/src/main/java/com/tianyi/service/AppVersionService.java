package com.tianyi.service;

import com.tianyi.bo.AppVersion;
import com.tianyi.dao.AppVersionDao;
import com.tianyi.mapper.AppVersionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 雪峰 on 2017/10/17.
 */
@Service("appVersionService")
public class AppVersionService {


    @Autowired
    private AppVersionDao appVersionDao;
    @Autowired
    private AppVersionMapper appVersionMapper;

    public List<AppVersion> getAppVersionList(String version, String os) {

        return appVersionDao.getAppVersionList(version, os);
    }

    public List<AppVersion> getAppVersions( final int page, final int pageSize){
        return appVersionDao.getAppVersions(page,pageSize);
    }

    public Integer getTotalNumber(){
        return appVersionDao.getTotalNumber();
    }



    public AppVersion getAppVersionById(long id){
        return appVersionDao.getById(id);
    }

    public void addAppVersion(AppVersion appVersion ){
        appVersionDao.add(appVersion);
    }

    public void editAppVersion(AppVersion appVersion){
        appVersionDao.update(appVersion);
    }

    public void delAppVersion(AppVersion appVersion){
        appVersionDao.delete(appVersion);
    }

  public AppVersion getRencentDownloadUrl(String os) {
    os = os.toUpperCase();
    if ("ANDROID".equals(os)) {
      os = "ANDORID";
    }
    Map<String,String> param = new HashMap<>(16);
    param.put("os",os);
    return appVersionMapper.getRencentDownloadUrl(param);
  }
}
