package com.tianyi.web.controller;

import com.tianyi.bo.KfzHistoryList;
import com.tianyi.bo.KfzQuestion;
import com.tianyi.bo.KfzResult;
import com.tianyi.bo.User;
import com.tianyi.bo.kangfuzi.UserEnc;
import com.tianyi.service.KfzService;
import com.tianyi.service.UserService;
import com.tianyi.web.model.PagedListModel;
import com.tianyi.util.HttpUtil;
import com.tianyi.util.JsonPathArg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by 安辉 on 2018/2/28.
 */
@RestController
@RequestMapping(value = "/kfz")
public class KangFuZiController {


    @Autowired
    UserService userService;

    @Autowired
    KfzService kfzService;

    @Value("${kangfuzi.url}")
    private String url;

    @Value("${kangfuzi.result.url}")
    private String kfzResultUrl;

    @Value("${kangfuzi.tyzndz.appId}")
    private String tyzndzAppId;
    @Value("${kangfuzi.tyzndz.appSecret}")
    private String tyzndzSecret;

    @Value("${kangfuzi.tyywz.appId}")
    private String tyywzAppId;
    @Value("${kangfuzi.tyywz.appSecret}")
    private String tyywzSecret;

    @Value("${kangfuzi.tyznzd.appId}")
    private String tyznzdAppId;
    @Value("${kangfuzi.tyznzd.appSecret}")
    private String tyznzdSecret;

    // @AuthRequired
    @RequestMapping("link")
    public List<Map<String, Object>> kfuLink(@RequestParam(value = "userId", required = true) String userId,
                                             @Value("#{request.getAttribute('lang')}") String lang,
                                             HttpServletRequest request) {

        String token = request.getHeader("X-Token");
        User user =userService.getUserByToken(token);

        try {
            return kfzService.getKfzLinks(Long.parseLong(userId),lang);
        } catch (Exception e) {
            return null;
        }
    }
    @Deprecated
    @RequestMapping("callback/znzd")
    public void tyznzd(@RequestParam(value = "user_enc", required = false) String user_enc) throws Exception {


        Map<String,Object> param = new HashMap();
        param.put("app_id",tyznzdAppId);
        param.put("action","get_user_info");
        param.put("user_enc",user_enc);

        String result= HttpUtil.post(kfzResultUrl,param);
        UserEnc userEnc= kfzService.Decrypt(user_enc,tyznzdSecret);

        // 1 保存康夫子，问诊结果
        KfzResult kfzResult = new KfzResult();
        kfzResult.setUserId(Long.parseLong(userEnc.user_id));
        kfzResult.setAppId(tyznzdAppId);
        kfzResult.setResult(result);

        kfzService.callback(kfzResult);
    }

    @Deprecated
    @RequestMapping("callback/ywz")
    public void tyywz(@RequestParam(value = "user_enc", required = false) String user_enc) throws Exception {



        Map<String,Object> param = new HashMap();
        param.put("app_id",tyywzAppId);
        param.put("action","get_user_info");
        param.put("user_enc",user_enc);

        String result= HttpUtil.post(kfzResultUrl,param);
        UserEnc userEnc= kfzService.Decrypt(user_enc,tyywzSecret);

        // 1 保存康夫子，问诊结果
        KfzResult kfzResult = new KfzResult();
        kfzResult.setUserId(Long.parseLong(userEnc.user_id));
        kfzResult.setAppId(tyywzAppId);
        kfzResult.setResult(result);

        kfzService.callback(kfzResult);
    }

    @RequestMapping(value = "callback",method = RequestMethod.POST)
    public void zndz(@JsonPathArg(value = "result") String result,
                     @JsonPathArg(value = "userId") String userId,
                     @JsonPathArg(value = "appId") String appId,
                     @JsonPathArg(value = "questionId") String questionId,
                     @JsonPathArg("sessionId") String sessionId) throws Exception {

        // 1 保存康夫子，问诊结果
        KfzResult kfzResult = new KfzResult();
        kfzResult.setUserId(Long.parseLong(userId));
        kfzResult.setAppId(appId);
        kfzResult.setResult(result);
        kfzResult.setQuestionId(questionId);
        kfzResult.setSessionId(sessionId);

        kfzService.callback(kfzResult);
    }

    @Deprecated
    @RequestMapping("callback/zndz")
    public void zndz(@RequestParam(value = "user_enc", required = false) String user_enc) throws Exception {



        Map<String,Object> param = new HashMap();
        param.put("app_id",tyzndzAppId);
        param.put("action","get_user_info");
        param.put("user_enc",user_enc);

        String result= HttpUtil.post(kfzResultUrl,param);
        UserEnc userEnc= kfzService.Decrypt(user_enc,tyzndzSecret);

        // 1 保存康夫子，问诊结果
        KfzResult kfzResult = new KfzResult();
        kfzResult.setUserId(Long.parseLong(userEnc.user_id));
        kfzResult.setAppId(tyzndzAppId);
        kfzResult.setResult(result);

        kfzService.callback(kfzResult);
    }

    @RequestMapping(value = "qa", method = RequestMethod.POST)
    public void addQuestionAndAnswer(@JsonPathArg("userId") Long userId,
                                     @JsonPathArg("appId") String appId,
                                     @JsonPathArg("question") String question,
                                     @JsonPathArg("answer") String answer,
                                     @JsonPathArg("questionId") String questionId,
                                     @JsonPathArg("sessionId") String sessionId) throws Exception {


        String appName="";
        if("5a7c0659bea7c06caf241b76".equals(appId)){
            appName="智能导诊";
        }else if("5a7c0641bea7c06caf241b75".equals(appId)){
            appName="预问诊";
        }else{
            appName="智能诊断";
        }

        KfzQuestion  que= new KfzQuestion();
        que.setKfzQid(questionId);
        que.setUserId(userId);
        que.setKfzAppid(appId);
        que.setKfzQuestionBody(question);
        que.setKfzAnswer(answer);
        que.setKfzAppName(appName);
        que.setSessionId(sessionId);

        kfzService.saveQuestion(que);
    }

    @ResponseBody
    @RequestMapping(value = "getKfzHistory", method = RequestMethod.GET)
    public Map getKfzHistory(@RequestParam(value = "session_id",required = false) String sessionId,
                                           @RequestParam(value = "p",required = false) Integer page,
                                           @RequestParam(value = "p_size",required = false) Integer size){
        List<KfzQuestion> result=kfzService.getKfzQuestionHistory(sessionId,page,size);
        //int total=kfzService.getTotalNumber(sessionId);
        //return new PagedListModel(result, total, total, size);
        Map finalResult=new HashMap();
        finalResult.put("list",result);
        return finalResult;
    }

    @ResponseBody
    @RequestMapping(value = "getKfzHistoryList", method = RequestMethod.GET)
    public PagedListModel getKfzHistory(@RequestParam(value = "p",required = false) Integer page,
                                        @RequestParam(value = "p_size",required = false) Integer size){
        List<KfzHistoryList> result=kfzService.getKfzHistoryList(page,size);
        int total=kfzService.getKfzHistoryTotalNum();
        return new PagedListModel(result, total, total, size);
    }

}
