package com.tianyi.web.controller;

import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * Created by 雪峰 on 2018/1/1.
 */
@Controller
@RequestMapping("/")
public class HomeController {

    private final RequestMappingHandlerMapping handlerMapping;
    @Autowired
    RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Autowired
    public HomeController( @Qualifier("requestMappingHandlerMapping")RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Map<String, String>> index() {
        List<Map<String, String>> requests = new ArrayList<Map<String, String>>();
        Map<RequestMappingInfo, HandlerMethod> tmp = this.handlerMapping.getHandlerMethods();
        Iterator<Map.Entry<RequestMappingInfo, HandlerMethod>> it = tmp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<RequestMappingInfo, HandlerMethod> entry = it.next();
            RequestMappingInfo key = entry.getKey();
            HandlerMethod handlerMethod = entry.getValue();
            Map<String, String> req = new HashMap<String, String>();
            // req.put("name", handlerMethod.getBeanType().getSimpleName() + "_" + handlerMethod.getMethod().getName());
            req.put("url", key.getPatternsCondition().toString());
            req.put("method", key.getMethodsCondition().toString());
            requests.add(req);
        }
        return requests;
    }

    @ResponseBody
    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public Map<String,Object> ping() {

        Map<String,Object> result = new HashMap();

        result.put("msg","ok");
        return result;
    }


    @ResponseBody
    @RequestMapping(value = "/ping", method = RequestMethod.POST)
    public Map<String,Object> pingPost() {

        Map<String,Object> result = new HashMap();
        result.put("msg","ok");
        return result;
    }

  /**
   * 安卓客户端获取当前服务器时间
   * @return 返回服务器当前时间
   */
  @ApiOperation(value="获取用户步数", notes="")
  @ResponseBody
  @RequestMapping(value = "/correctTime", method = RequestMethod.GET)
  public Map<String,Object> correctTime() {
    Map<String,Object> result = new HashMap<>(16);
    result.put("server_time",new Date());
    return result;
  }
}
