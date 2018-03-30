package com.tianyi.web.controller.miaojiankang;

import com.alibaba.fastjson.JSON;
import com.tianyi.bo.User;
import com.tianyi.bo.miaojiankang.UserDevice;
import com.tianyi.bo.miaojiankang.UserDeviceData;
import com.tianyi.service.AccountService;
import com.tianyi.service.UserDataService;
import com.tianyi.service.miaojiankang.UserDeviceDataService;
import com.tianyi.service.miaojiankang.UserDeviceService;
import com.tianyi.web.AuthRequired;
import com.tianyi.web.controller.vo.UserDeviceDataModel;
import com.tianyi.web.controller.vo.UserDeviceDataResult;
import com.tianyi.web.model.ActionResult;
import com.tianyi.web.util.JsonPathArg;
import com.tianyi.web.util.Tools;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by anhui on 2018/3/22.
 */
@RestController
@RequestMapping(value = "/mjk")
public class UserDeviceDataController {

    @Resource
    UserDeviceService userDeviceService;

    @Resource
    UserDeviceDataService userDeviceDataService;

    private static String WATCH="10096";
    private static String BRACELET="10097";


    /***
     * 保存设备数据
     * @param request
     * @param currentUser
     * @return
     * @throws IOException
     */
    @AuthRequired
    @RequestMapping(value = {"save", "api/save"}, method = RequestMethod.POST)
    public Map<String, String> add(HttpServletRequest request, @Value("#{request.getAttribute('currentUser')}") User currentUser) throws IOException {

        List<UserDeviceData> deviceList = Tools.getParamMap(request);
        for (UserDeviceData device : deviceList) {

            device.setCreatedOn(new Date());
            device.setUpdatedOn(new Date());
            device.setUpdatedTimestamp(new Date().getTime());
            device.setUserId(currentUser.getId());
            userDeviceDataService.addUserDeviceData(device);
        }
        Map<String, String> result = new HashedMap();
        result.put("msg", "ok");
        return result;
    }


    /****
     * 上传我的设备
     * @param deviceId
     * @param deviceName
     * @param uuid
     * @param deviceSn
     * @param desUrl
     * @param linkType
     * @param deviceDes
     * @param bindNum
     * @param bindStatus
     * @param logo
     * @param tid
     * @param typeName
     * @param typeDesc
     * @param typeLogo
     * @param currentUser
     * @return
     * @throws IOException
     */
    @AuthRequired
    @RequestMapping(value = {"device", "api/device"}, method = RequestMethod.POST)
    public Map<String, String> add(@JsonPathArg("deviceId") String deviceId,
                                   @JsonPathArg("deviceName") String deviceName,
                                   @JsonPathArg("uuid") String uuid,
                                   @JsonPathArg("deviceSn") String deviceSn,
                                   @JsonPathArg("desUrl") String desUrl,
                                   @JsonPathArg("linkType") String linkType,
                                   @JsonPathArg("deviceDes") String deviceDes,
                                   @JsonPathArg("bindNum") String bindNum,
                                   @JsonPathArg("bindStatus") String bindStatus,
                                   @JsonPathArg("logo") String logo,
                                   @JsonPathArg("tid") String tid,
                                   @JsonPathArg("typeName") String typeName,
                                   @JsonPathArg("typeDesc") String typeDesc,
                                   @JsonPathArg("typeLogo") String typeLogo,
                                   @Value("#{request.getAttribute('currentUser')}") User currentUser) throws IOException {


        UserDevice target = userDeviceService.getUserDeviceByDeviceId(deviceId, currentUser.getId());
        if (target != null) {
            Map<String, String> result = new HashedMap();
            result.put("msg", "ok");
            return result;
        } else {

            UserDevice device = new UserDevice();

            device.setCreatedOn(new Date());
            device.setUpdatedOn(new Date());
            device.setUpdatedTimestamp(new Date().getTime());
            device.setUserId(currentUser.getId());
            device.setDeviceId(deviceId);
            device.setDeviceName(deviceName);
            device.setUuid(uuid);
            device.setDeviceSn(deviceSn);
            device.setDesUrl(desUrl);
            device.setLinkType(linkType);
            device.setDeviceDes(deviceDes);
            device.setBindNum(bindNum);
            device.setBindStatus(bindStatus);
            device.setLogo(logo);
            device.setTid(tid);
            device.setTypeName(typeName);
            device.setTypeDesc(typeDesc);
            device.setTypeLogo(typeLogo);
            userDeviceService.addUserDevice(device);

            Map<String, String> result = new HashedMap();
            result.put("msg", "ok");
            return result;
        }
    }


    /****
     * 获取我的数据来源
     * @param currentUser
     * @param from
     * @return
     * @throws IOException
     */
    @AuthRequired
    @RequestMapping(value = {"device", "api/device"}, method = RequestMethod.GET)
    public Object getDevice(@Value("#{request.getAttribute('currentUser')}") User currentUser,
                            @RequestParam(value = "typeId", required = false) String typeid,
                            @RequestHeader("X-Client") String from) throws IOException {

        List<UserDevice> userDevices = new ArrayList<>();

        // 默认数据来源
        UserDevice device = new UserDevice();
        device.setStatus(1);
        if ("ios".equals(from.toLowerCase())) {
            device.setDeviceName("IPhone");
        } else {
            device.setDeviceName("Android");
        }
        device.setCreatedOn(currentUser.getCreatedOn());
        device.setDeviceId(from + "-" + currentUser.getId());
        device.setUserId(currentUser.getId());
        userDevices.add(device);

        // 如果没有默认设备，手机就时默认设备
        List<UserDevice> targetList = new ArrayList<>();
        if (typeid.equals(WATCH) || typeid.equals(BRACELET)) {
            targetList = userDeviceService.getUserDevice(currentUser.getId());
        } else {
            targetList = userDeviceService.getUserDeviceByTypeId(currentUser.getId(), typeid);
        }

        for (UserDevice target : targetList) {
            if (target.getStatus() == 1) {
                device.setStatus(0);
            }
        }
        for (UserDevice target : targetList) {
            userDevices.add(target);
        }
        return userDevices;
    }

    /****
     * 获取我的数据来源
     * @param currentUser
     * @param from
     * @return
     * @throws IOException
     */
    @AuthRequired
    @RequestMapping(value = {"device/all", "api/device/all"}, method = RequestMethod.GET)
    public Object getDeviceAll(@Value("#{request.getAttribute('currentUser')}") User currentUser,
                               @RequestHeader("X-Client") String from) throws IOException {

        List<UserDevice> userDevices = new ArrayList<>();

        // 默认数据来源
        UserDevice device = new UserDevice();
        device.setStatus(1);
        if ("ios".equals(from.toLowerCase())) {
            device.setDeviceName("IPhone");
        } else {
            device.setDeviceName("Android");
        }
        device.setDeviceId(from + "-" + currentUser.getId());
        device.setUserId(currentUser.getId());
        userDevices.add(device);

        // 如果没有默认设备，手机就时默认设备
        List<UserDevice> targetList = userDeviceService.getUserDeviceAll(currentUser.getId());
        for (UserDevice target : targetList) {
            if (target.getStatus() == 1) {
                device.setStatus(0);
            }
        }
        for (UserDevice target : targetList) {
            userDevices.add(target);
        }
        return userDevices;
    }


    /***
     * 切换默认设备
     * @param deviceId
     * @param currentUser
     * @param from
     * @return
     * @throws IOException
     */
    @AuthRequired
    @RequestMapping(value = {"device/default", "api/device/default"}, method = RequestMethod.POST)
    public Object setDefaultDevice(@JsonPathArg("deviceId") String deviceId, @Value("#{request.getAttribute('currentUser')}") User currentUser, @RequestHeader("X-Client") String from) throws IOException {


        long userId =currentUser.getId();

        List<UserDevice> targetList = userDeviceService.getUserDeviceAll(userId);

        UserDevice device = userDeviceService.getUserDeviceByDeviceId(deviceId, userId);

        // device ==null 手机访问
        if (device == null) {
            for (UserDevice target : targetList) {
                // 4,7 步数数据源切换的设备
                if ((target.getStatus() == 1 && target.getTid().equals(WATCH)) ||
                        (target.getStatus() == 1 && target.getTid().equals(BRACELET))) {
                    target.setStatus(0);
                    return userDeviceService.updateDevice(target);
                }
            }
        } else {

            // 设备id 存在设备，并且是同类型设备
            for (UserDevice target : targetList) {

                // 同类型默认设备
                if (target.getDeviceId().equals(deviceId) && device.getTid().equals(target.getTid())) {
                    target.setStatus(1);
                    userDeviceService.updateDevice(target);

                }

                // 同类非默认设备
                if(!target.getDeviceId().equals(deviceId) && device.getTid().equals(target.getTid())){
                    target.setStatus(0);
                    userDeviceService.updateDevice(target);
                }
            }
        }
        return new UserDevice();
    }


    /***
     * 切换默认设备
     * @param uuid
     * @param currentUser
     * @param from
     * @return
     * @throws IOException
     */
    @AuthRequired
    @RequestMapping(value = {"device/unbind", "api/device/unbind"}, method = RequestMethod.POST)
    public Object unBindDevice(@JsonPathArg("uuid") String uuid, @Value("#{request.getAttribute('currentUser')}") User currentUser, @RequestHeader("X-Client") String from) throws IOException {

        Map<String, Object> result = new HashedMap();

        List<UserDevice> targetList = userDeviceService.getUserDeviceAll(currentUser.getId());
        for (UserDevice target : targetList) {
            if (target.getUuid().equals(uuid)) {
                target.setStatus(2);
                userDeviceService.updateDevice(target);
            }
        }
        result.put("msg", "ok");
        return result;
    }


    /****
     * 获取设备数据历史步数
     * @param currentUser
     * @return
     */
    @AuthRequired
    @RequestMapping(value = {"device/data", "api/device/data"}, method = RequestMethod.GET)
    public Object getUserDeviceDataHistory(@Value("#{request.getAttribute('currentUser')}") User currentUser,
                                           @RequestParam(value = "page", required = true) Integer page,
                                           @RequestParam(value = "pageSize", required = true) Integer pageSize) {

        // 处理分页
        if (page <= 1) {
            page = 1;
        }
        long userId=currentUser.getId();

        // 汇总天数
        List<String> dayList = new ArrayList<>();

        // 最初的结果
        List<Map<String, String>> resultMapList = new ArrayList();

        // 步数
        List<UserDeviceData> resultList = userDeviceDataService.getUserDeviceDataHistory(userId, page, pageSize);

        // Kral卡路里
        List<UserDeviceData> kralList = userDeviceDataService.getUserDeviceKralHistory(userId, page, pageSize);


        for (UserDeviceData result : resultList) {

            String date = new SimpleDateFormat("yyyy-MM-dd").format(result.getCreatedOn());
            String time = new SimpleDateFormat("HH:mm:ss").format(result.getCreatedOn());
            String value = result.getData();


            Map<String, String> resultMap = new HashMap();
            resultMap.put("date", date);
            resultMap.put("time", time);
            resultMap.put("value", value);
            resultMap.put("kral","0");

            // 添加卡路里
            for (UserDeviceData kral : kralList) {

                String target = new SimpleDateFormat("yyyy-MM-dd").format(kral.getCreatedOn());
                if (date.equals(target)) {
                    resultMap.put("kral", kral.getData());
                    continue;
                }
            }

            resultMapList.add(resultMap);
        }


        return resultMapList;

        /*
        // 按照天数分组
        for(Map<String,String> group: resultMapList){
            String date=group.get("date");
            if(!dayList.contains(date)){
                dayList.add(date);
            }
        }

        // 根据天 过滤 最初的结果，返回数据
        List<Map<String,Object>> groupResultList = new ArrayList<>();
        for(String day:dayList){
            Map<String,Object> grapResultListItem=new HashedMap();
            grapResultListItem.put("day",day);
            List<Map<String,String>> groupResultItem= new ArrayList<>();
            for(Map<String,String> map:resultMapList){
               String target=map.get("date");
                if(target.equals(day)){
                    groupResultItem.add(map);
                }
            }
            grapResultListItem.put("stepList",groupResultItem);
            groupResultList.add(grapResultListItem);
        }
        return groupResultList;
        */
    }


    /****
     * 获取心率
     * @param currentUser
     * @return
     */
    @AuthRequired
    @RequestMapping(value = {"device/heart", "api/device/heart"}, method = RequestMethod.GET)
    public Object getUserDeviceKralHistory(@Value("#{request.getAttribute('currentUser')}") User currentUser,
                                           @RequestParam(value = "page", required = true) Integer page,
                                           @RequestParam(value = "pageSize", required = true) Integer pageSize) {

        // 处理分页
        if (page <= 1) {
            page = 1;
        }


        // 汇总天数
        List<String> dayList = new ArrayList<>();

        // 最初的结果
        List<Map<String, String>> resultMapList = new ArrayList();

        // 心率
        List<UserDeviceData> resultList = userDeviceDataService.getUserDeviceHeartRateHistory(currentUser.getId(), page, pageSize);

        //
        List<Map<String, Object>> groupResultList = getMaps(dayList, resultMapList, resultList);
        return groupResultList;

    }

    /****
     * 获取设备数据 高压 低压 心率
     * @param currentUser
     * @return
     */
    @AuthRequired
    @RequestMapping(value = {"device/blood", "api/device/blood"}, method = RequestMethod.GET)
    public Object getUserDeviceBloodHistory(@Value("#{request.getAttribute('currentUser')}") User currentUser,
                                            @RequestParam(value = "page", required = true) Integer page,
                                            @RequestParam(value = "pageSize", required = true) Integer pageSize) throws ParseException {

        if (page <= 1) {
            page = 1;
        }

        long userId = currentUser.getId();


        // 汇总天数
        List<String> dayList = new ArrayList<>();

        // 最初的结果
        List<Map<String, String>> resultMapList = new ArrayList();

        // 低压
        List<UserDeviceData> resultList = userDeviceDataService.getUserDeviceBloodLowHistory(userId, page, pageSize);

        // 高压
        List<UserDeviceData> resultHighList = userDeviceDataService.getUserDeviceBloodHighHistory(userId, page, pageSize);

        // 心率
        List<UserDeviceData> heartList = userDeviceDataService.getUserDeviceHeartRateHistory(userId, page, pageSize);


        for (UserDeviceData result : resultList) {
            String date = new SimpleDateFormat("yyyy-MM-dd").format(result.getCreatedOn());
            String time = new SimpleDateFormat("HH:mm:ss").format(result.getCreatedOn());
            String value = result.getData();
            for (UserDeviceData resultHigh : resultHighList) {
                String dateTarget = new SimpleDateFormat("yyyy-MM-dd").format(resultHigh.getCreatedOn());
                String timeTarget = new SimpleDateFormat("HH:mm:ss").format(resultHigh.getCreatedOn());
                String valueTarget = resultHigh.getData();
                if (date.equals(dateTarget) && time.equals(timeTarget)) {
                    result.setData(valueTarget + "/" + value);
                    continue;
                }
            }
        }

        List<Map<String, Object>> groupResultList = getMaps(dayList, resultMapList, resultList);

        for (Map<String, Object> heart : groupResultList) {

            List<Map<String, Object>> items = (List<Map<String, Object>>) heart.get("list");

            for (Map<String, Object> item : items) {

                String targetDate = item.get("date").toString();
                String targetTime = item.get("time").toString();

                Date targetDt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(targetDate + " " + targetTime);

                for (UserDeviceData ht : heartList) {

                    String date = new SimpleDateFormat("yyyy-MM-dd").format(ht.getCreatedOn());
                    String time = new SimpleDateFormat("HH:mm:ss").format(ht.getCreatedOn());

                    Date dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date + " " + time);

                    if (date.equals(targetDate) && time.equals(targetTime)) {
                        item.put("heart", ht.getData());
                        break;
                    }
                }
            }
        }
        return groupResultList;

    }

    /****
     * 获取设备数据体重
     * @param currentUser
     * @return
     */
    @AuthRequired
    @RequestMapping(value = {"device/weight", "api/device/weight"}, method = RequestMethod.GET)
    public Object getUserDeviceWeightHistory(@Value("#{request.getAttribute('currentUser')}") User currentUser,
                                             @RequestParam(value = "page", required = true) Integer page,
                                             @RequestParam(value = "pageSize", required = true) Integer pageSize) {

        if (page <= 1) {
            page = 1;
        }

        long userId=currentUser.getId();

        // 汇总天数
        List<String> dayList = new ArrayList<>();

        // 最初的结果
        List<Map<String, String>> resultMapList = new ArrayList();

        // 体重
        List<UserDeviceData> resultList = userDeviceDataService.getUserDeviceWeightHistory(userId, page, pageSize);

        List<Map<String, Object>> groupResultList = getMaps(dayList, resultMapList, resultList);
        return groupResultList;
    }


    /***
     * 请求结果按天分组
     * @param dayList
     * @param resultMapList
     * @param resultList
     * @return
     */
    private List<Map<String, Object>> getMaps(List<String> dayList, List<Map<String, String>> resultMapList, List<UserDeviceData> resultList) {
        for (UserDeviceData result : resultList) {

            String date = new SimpleDateFormat("yyyy-MM-dd").format(result.getCreatedOn());
            String time = new SimpleDateFormat("HH:mm:ss").format(result.getCreatedOn());
            String value = result.getData();


            Map<String, String> resultMap = new HashMap();
            resultMap.put("date", date);
            resultMap.put("time", time);
            resultMap.put("value", value);

            resultMapList.add(resultMap);
        }


        // 按照天数分组
        for (Map<String, String> group : resultMapList) {
            String date = group.get("date");
            if (!dayList.contains(date)) {
                dayList.add(date);
            }
        }

        // 根据天 过滤 最初的结果，返回数据
        List<Map<String, Object>> groupResultList = new ArrayList<>();
        for (String day : dayList) {
            Map<String, Object> grapResultListItem = new HashedMap();
            grapResultListItem.put("day", day);
            List<Map<String, String>> groupResultItem = new ArrayList<>();
            for (Map<String, String> map : resultMapList) {
                String target = map.get("date");
                if (target.equals(day)) {
                    groupResultItem.add(map);
                }
            }
            grapResultListItem.put("list", groupResultItem);
            groupResultList.add(grapResultListItem);
        }
        return groupResultList;
    }
}
