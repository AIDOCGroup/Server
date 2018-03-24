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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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


    @AuthRequired
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public ActionResult add(HttpServletRequest request, @Value("#{request.getAttribute('currentUser')}") User currentUser) throws IOException {

        List<UserDeviceData> deviceList = Tools.getParamMap(request);

        for (UserDeviceData device : deviceList) {

            device.setCreatedOn(new Date());
            device.setUpdatedOn(new Date());
            device.setUpdatedTimestamp(new Date().getTime());
            device.setUserId(currentUser.getId());
            userDeviceDataService.addUserDeviceData(device);
        }
        return new ActionResult();
    }


    @AuthRequired
    @RequestMapping(value = "device", method = RequestMethod.POST)
    public ActionResult add(@JsonPathArg("deviceId") String deviceId,
                            @JsonPathArg("deviceName") String deviceName,
                            @Value("#{request.getAttribute('currentUser')}") User currentUser) throws IOException {


        UserDevice device = new UserDevice();

        device.setCreatedOn(new Date());
        device.setUpdatedOn(new Date());
        device.setUpdatedTimestamp(new Date().getTime());
        device.setUserId(currentUser.getId());
        device.setDeviceId(deviceId);
        device.setDeviceName(deviceName);

        userDeviceService.addUserDevice(device);
        return new ActionResult();
    }


    @AuthRequired
    @RequestMapping(value = "device", method = RequestMethod.GET)
    public Object getDevice(@Value("#{request.getAttribute('currentUser')}") User currentUser,
                            @RequestHeader("X-Client") String from) throws IOException {

        List<UserDevice> userDevices = new ArrayList<>();

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

        List<UserDevice> targetList = userDeviceService.getUserDevice(currentUser.getId());

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
    @RequestMapping(value = "device/default", method = RequestMethod.POST)
    public Object setDefaultDevice(@JsonPathArg("deviceId") String deviceId, @Value("#{request.getAttribute('currentUser')}") User currentUser, @RequestHeader("X-Client") String from) throws IOException {

        List<UserDevice> targetList = userDeviceService.getUserDevice(currentUser.getId());

        for (UserDevice target : targetList) {
            if (target.getDeviceId().equals(deviceId)) {
                if (target.getStatus() == 0) {
                    target.setStatus(1);
                    return userDeviceService.updateDevice(target);
                }
            } else {
                if (target.getStatus() == 1) {
                    target.setStatus(0);
                    return userDeviceService.updateDevice(target);
                }
            }
        }
        return new UserDevice();
    }

    /****
     * 获取设备数据历史
     * @param currentUser
     * @return
     */
    @RequestMapping(value = "device/data", method = RequestMethod.GET)
    public Object getUserDeviceDataHistory(@Value("#{request.getAttribute('currentUser')}") User currentUser,
                                           @RequestParam(value = "page", required = true) Integer page,
                                           @RequestParam(value = "pageSize", required = true) Integer pageSize) {


        List<UserDeviceData> resultList= userDeviceDataService.getUserDeviceDataHistory(currentUser.getId(),page,pageSize);

        return resultList;
    }
}
