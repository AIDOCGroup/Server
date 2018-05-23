package com.tianyi.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.tianyi.framework.util.Localize;
import com.tianyi.mapper.AiSkinMapper;
import com.tianyi.service.AiSkinService;
import com.tianyi.util.HttpUtils;
import com.tianyi.vo.AiSkinVo;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:CDH
 * Date:2018/5/10
 */
@Service
public class AiSkinServiceImpl implements AiSkinService{

    @Value("${skin.ai.check.yyzn.appCode}")
    private String appcode;

    @Autowired
    AiSkinMapper aiSkinMapper;

    private static String code = "return code";

    @Override
    public AiSkinVo aSkin(String aiUrl,long id) {
        AiSkinVo aiSkinVo = new AiSkinVo();

        //脸部关键区域检测
        String faceRegion = faceRegion(aiUrl);
        if (faceRegion.contains(code)){
            aiSkinVo.setErr_code(1);
            aiSkinVo.setErr_msg("网络异常");
            return aiSkinVo;
        }

        //面部疾病检测
        String faceDiagnose = faceDiagnose(aiUrl);
        aiSkinVo.setFaceDiagnose(faceDiagnose);

        //面部斑点检测
        String faceSpot = faceSpot(faceRegion, aiUrl);
        if ("".equals(faceSpot) || faceSpot.length() < 30 ){
            aiSkinVo.setSpotNum("");
            aiSkinVo.setSpotUrl("");
        }else {
            JSONObject jsonObject = JSON.parseObject(faceSpot);
            aiSkinVo.setSpotNum(jsonObject.get("spot_num").toString());
            aiSkinVo.setSpotUrl(jsonObject.get("spot").toString());
        }

        //脸部基本属性
        String faceAttr = faceAttr(aiUrl);
        aiSkinVo.setFaceAttr(faceAttr);

        //年龄检测
        String faceAge = faceAge(aiUrl);
        aiSkinVo.setFaceAge(faceAge);

        //面部皱纹检测
        String faceWrinkle = faceWrinkle(faceRegion, aiUrl);
        if ("".equals(faceWrinkle) || faceWrinkle.length() < 30 ){
            aiSkinVo.setWrinkleNum("");
            aiSkinVo.setWrinkleUrl("");
        }else {
            JSONObject jsonObject = JSON.parseObject(faceSpot);
            aiSkinVo.setWrinkleNum(jsonObject.get("wrinkle_num").toString());
            aiSkinVo.setWrinkleUrl(jsonObject.get("wrinkle").toString());
        }
        //面部油干性检测
        String faceDry = faceDry(faceRegion, aiUrl);
        aiSkinVo.setFaceDry(faceDry);
        aiSkinVo.setUpdatedTimestamp(DateTime.now().toDate().getTime());
        aiSkinVo.setUserId(id);

        int age = 10;
        if ("".equals(faceAge)){
            aiSkinVo.setScore(100);
        }else {
            age = Integer.parseInt(faceAge)/2;
        }

        if (faceDiagnose.length() > 30){
            Gson gson = new Gson();
            Map<String, List<Map<String, String>>> map = new HashMap<String, List<Map<String, String>>>();
            map = gson.fromJson(faceDiagnose, map.getClass());
            List<Map<String, String>> disease = map.get("disease");
            int size = disease.size()*5;

            aiSkinVo.setScore(100-age-size);
            aiSkinMapper.insertAiSkin(aiSkinVo);

            if (map.size() >0){
            StringBuffer buf = new StringBuffer();
            for (Map memberAttribute : disease) {
                Map memberAttribute1 = memberAttribute;
                buf.append(memberAttribute1.get("class")).append(",");
            }
                aiSkinVo.setFaceDiagnose(buf.substring(0, buf.length() - 1));
            }
        }else {
            aiSkinVo.setScore(90);
            aiSkinMapper.insertAiSkin(aiSkinVo);
            aiSkinVo.setFaceDiagnose("");
        }
        aiSkinVo.setErr_code(0);
        aiSkinVo.setErr_msg(Localize.getMessage("common.message.success"));
        return aiSkinVo;
    }

    /**
     * 脸部关键区域检测
     * @return
     */
    public String faceRegion(String aiUrl){

        String host = "http://facediag.market.alicloudapi.com";
        String path = "/api/face_region/";
        String method = "POST";

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("image_url", aiUrl);
        bodys.put("type", "0");

        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            String s = response.toString();
            String region = EntityUtils.toString(response.getEntity());
            return region;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 面部斑点检测
     * @return
     */
    public String faceSpot(String region,String aiUrl){
        String host = "http://facediag.market.alicloudapi.com";
        String path = "/api/face_spot/";
        String method = "POST";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("image_url", aiUrl);
        bodys.put("region", region);
        bodys.put("type", "0");

        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            String spot = EntityUtils.toString(response.getEntity());
            return spot;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 面部疾病检测
     * @return
     */
    public String faceDiagnose(String aiUrl){
        String host = "http://facediag.market.alicloudapi.com";
        String path = "/api/face_diagnose/";
        String method = "POST";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("image_url", aiUrl);
        bodys.put("type", "0");

        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            String disease = EntityUtils.toString(response.getEntity());
            return disease;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 脸部基本属性
     * @return
     */
    public String faceAttr(String aiUrl){
        String host = "http://facediag.market.alicloudapi.com";
        String path = "/api/face_attr/";
        String method = "POST";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("image_url", aiUrl);
        bodys.put("type", "0");

        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            String attr = EntityUtils.toString(response.getEntity());
            return attr;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 年龄检测
     * @return
     */
    public String faceAge(String aiUrl){
        String host = "http://facediag.market.alicloudapi.com";
        String path = "/api/face_age";
        String method = "POST";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("image_url", aiUrl);
        bodys.put("type", "0");
        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            //获取response的body
            String age = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = JSON.parseObject(age);
            String fa = jsonObject.get("age").toString();
            return fa;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 面部皱纹检测
     * @return
     */
    public String faceWrinkle(String region,String aiUrl){
        String host = "http://facediag.market.alicloudapi.com";
        String path = "/api/face_wrinkle/";
        String method = "POST";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("image_url", "image_url");
        bodys.put("region",region);
        bodys.put("type", "0");

        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            String s = EntityUtils.toString(response.getEntity());
            return s;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 面部油干性检测
     * @return
     */
    public String faceDry(String region,String aiUrl){
        String host = "http://facediag.market.alicloudapi.com";
        String path = "/api/face_dry/";
        String method = "POST";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("image_url", aiUrl);
        bodys.put("region", region);
        bodys.put("type", "0");

        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            String dryType = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = JSON.parseObject(dryType);
            String status = jsonObject.get("dry_type").toString();
            return status;
        } catch (Exception e) {
            return "";
        }
    }
}
