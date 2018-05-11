package com.tianyi.web;

import com.tianyi.bo.User;
import com.tianyi.bo.enums.UserType;
import com.tianyi.service.I18nService;
import com.tianyi.service.UserService;
import com.tianyi.util.SignatureUtil;
import com.tianyi.util.Tools;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by lingqingwan on 12/29/15
 */
@Component
public class AuthHandlerInterceptor implements HandlerInterceptor {
    @Resource
    UserService userService;
    @Resource
    I18nService i18nService;
    @Value("${auth.domain}")
    private String authDomain;


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {

        httpServletResponse.addHeader("Access-Control-Allow-Origin", authDomain);
//        httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.addHeader("Access-Control-Allow-Headers", "x-requested-with,content-type,X-Token,X-App-Name ");

        String method = httpServletRequest.getMethod();



        try {
            // 1 判断版本号
            String version = httpServletRequest.getHeader("X-Version");
            String client = httpServletRequest.getHeader("X-Client");

            /*IOS ANDORID ADMIN H5*/
            if(client ==null || client.length()<=0){
                //return false;
            }

            Integer num = getVersion(version);

            // 1.1 客户端

            if (((num >= getVersion("1.0.5")||num < getVersion("1.0.0")) && ("ios".equals(client.toLowerCase())))
                || ((num >= getVersion("1.0.7")||num < getVersion("1.0.0")) && ("andorid".equals(client.toLowerCase())))
                    ) {

                // 2 处理get参数
                HashedMap param = new HashedMap();
                if ("get".equals(method.toLowerCase())) {
                    // 暂不做处理
                }
                if ("post".equals(method.toLowerCase()) || "put".equals(method.toLowerCase())) {
                    // 2.1 处理post参数
                    BufferedReader reader = new BufferedReader(new InputStreamReader(httpServletRequest.getInputStream()));
                    StringBuffer buffer = new StringBuffer();
                    String line = " ";
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }

                    String bufferStr = buffer.toString();
                    if (bufferStr.endsWith("=")) {
                        bufferStr = bufferStr.substring(0, bufferStr.length() - 1);
                    }

                    if (bufferStr != null && bufferStr.length() > 0) {
                        Map<String, Object> params = Tools.parserJson2Map(bufferStr);
                        Iterator<Map.Entry<String, Object>> it = params.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry<String, Object> entry = it.next();
                            param.put(entry.getKey(), entry.getValue());
                        }
                    }
                }
                // 2.2 处理header
                String timestamp = httpServletRequest.getHeader("X-Timestamp");

                String token = httpServletRequest.getHeader("X-Token");

                if (timestamp != null && !timestamp.toString().isEmpty()) {

                }
                param.put("X-Timestamp", timestamp);
                param.put("X-Token", token);
                param.remove("X-Signature");

                // 1 加密key
                String secretKey = "ys/XvaP+LxVS7vXtzWnaS2CRYqmIG3HIgkMKBaBs";

                // 2 参数
                HashMap<String, String> parameters = new HashMap<String, String>();

                // 3 生成时间戳
                Date d = new Date();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String str = df.format(d) + "T";
                df = new SimpleDateFormat("HH");
                String dateStr = ((Integer.parseInt(df.format(d))) - 8) + ":";
                df = new SimpleDateFormat("mm:ss");
                String isoDate = str + dateStr + df.format(d) + "Z";

                // 5 计算参数
                String formattedParameters = SignatureUtil.calculateStringToSignV2(param);

                // 6 签名
                String signature = SignatureUtil.sign(formattedParameters, secretKey);
                String targetSignature = httpServletRequest.getHeader("X-Signature");

                // 7 验证签名
                if (!signature.equals(targetSignature)) {
                    return false;
                }
            }
        } catch (Exception ex) {
            //return false;
        }
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            AuthRequired authRequired = ((HandlerMethod) handler).getMethodAnnotation(AuthRequired.class);
            AdminRequired adminRequired=((HandlerMethod) handler).getMethodAnnotation(AdminRequired.class);

            String language = httpServletRequest.getHeader("X-LANG");
            String lang = "zh";
            if (language != null && StringUtils.isNotEmpty(language) && "1".equals(language)) {
                lang = "en";
            }

            httpServletRequest.setAttribute("lang", lang);
            LocaleContextHolder.setLocale(lang.equals("en") ? Locale.US : Locale.CHINESE);
            if (authRequired == null&& adminRequired == null) {
                return true;
            }
            String token = httpServletRequest.getHeader("X-Token");
            if (StringUtils.isBlank(token)) {
                token = httpServletRequest.getParameter("token");
            }
            if (StringUtils.isBlank(token)) {
                throw new UnauthorizedException(i18nService.getMessage("" + 109, lang));
            }


            User user = userService.getUserByToken(token);
            if (user == null) {
                throw new UnauthorizedException(i18nService.getMessage("" + 109, lang));
            }
            if(adminRequired!=null){
                //System.out.println("测试userType:::"+user.getUserType());
                //System.out.println("测试用户姓名:::"+user.getNickname());
                if(user.getUserType()!=UserType.ADMIN){
                    throw new UnauthorizedException(i18nService.getMessage(""+124,lang));
                }
            }
            httpServletRequest.setAttribute("currentUser", user);
            if (user.getUserLanguage() == 1) {
                lang = "en";
            }

            httpServletRequest.setAttribute("lang", lang);

            LocaleContextHolder.setLocale(lang.equals("en") ? Locale.US : Locale.CHINESE);
        }
        return true;
    }

    private Integer getVersion(String version) {
        Integer num;
        version = version.replace(".", "");
        num = Integer.parseInt(version);
        return num;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
