package me.aidoc.appserver;

import com.google.common.collect.Maps;
import me.aidoc.appserver.common.Localize;
import me.aidoc.appserver.common.exception.ResponseCodeEnum;
import me.aidoc.appserver.common.utils.JSonUtil;
import me.aidoc.appserver.common.utils.RequestUtil;
import me.aidoc.appserver.po.user.User;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author vliu
 * @create 2018-07-07 13:45
 **/
public class BaseController {

    /* header 常量定义 */
    private static final String ENCODING_PREFIX = "encoding";
    public static final String NOCACHE_PREFIX = "no-cache";
    private static final String ENCODING_DEFAULT = "UTF-8";
    private static final boolean NOCACHE_DEFAULT = true;
    private static int Expires = 100;

    /**
     * 状态码:成功
     */
    private static final int CODE_SUCCESS = ResponseCodeEnum.SUCCESS.getCode();

    private static final String KEY_CODE = "code";

    private static final String KEY_MESSAGE = "msg";

    private static final String KEY_DATA = "data";

    /**
     * 调用成功，输出JSON
     *
     * @param response
     * @param tip 提示信息
     * @param data 调用成功后,返回的业务数据(必须json格式)
     */
    public void renderSuccessJson(HttpServletResponse response, final String tip, final Object data) {
        Map<String,Object> retMap = Maps.newLinkedHashMap();
        retMap.put(KEY_CODE, CODE_SUCCESS);
        if(tip != null){
            retMap.put(KEY_MESSAGE,tip);
        }
        if(data!=null){
            retMap.put(KEY_DATA,data);
        }
        render(response, "application/json", JSonUtil.toJson(retMap), "no-cache:false");
    }

    /**
     * 调用成功，输出JSON
     * @param response
     * @param data
     */
    public void renderSuccessJson(HttpServletResponse response,Object data){
        renderSuccessJson(response, Localize.getMessage("common.message.success"),data);
    }

    /**
     * 调用失败，输出JSON
     *
     * @param response
     * @param errCode
     */
    public void renderFailJson(HttpServletResponse response,ResponseCodeEnum errCode) {
        Map<String,Object> retMap = new LinkedHashMap<>(2);
        retMap.put(KEY_CODE, errCode.getCode());
        retMap.put(KEY_MESSAGE, errCode.getMessage());
        render(response, "application/json", JSonUtil.toJson(retMap), "no-cache:false");
    }




    /**
     * 直接输出内容的简便函数.
     *
     * eg. render("text/plain", "hello", "encoding:GBK"); render("text/plain",
     * "hello", "no-cache:false"); render("text/plain", "hello", "encoding:GBK",
     * "no-cache:false");
     *
     * @param headers
     *            可变的header数组，目前接受的值为"encoding:"或"no-cache:",默认值分别为UTF-8和true .
     */
    private void render(HttpServletResponse response,final String contentType, final String content, final String... headers) {
        render(response,200,contentType,content,headers);
    }

    /**
     *
     * @param response
     * @param status
     * @param contentType
     * @param content
     * @param headers
     */
    private void render(HttpServletResponse response,int status,final String contentType, final String content,
                               final String... headers) {
        try {
            // 分析headers参数
            String encoding = ENCODING_DEFAULT;
            boolean noCache = NOCACHE_DEFAULT;
            for (String header : headers) {
                String headerName = StringUtils.substringBefore(header, ":");
                String headerValue = StringUtils.substringAfter(header, ":");

                if (StringUtils.equalsIgnoreCase(headerName, ENCODING_PREFIX)) {
                    encoding = headerValue;
                } else if (StringUtils.equalsIgnoreCase(headerName, NOCACHE_PREFIX)) {
                    noCache = Boolean.parseBoolean(headerValue);
                } else
                    throw new IllegalArgumentException(headerName
                            + "不是一个合法的header类型");
            }
            // 设置headers参数
            String fullContentType = contentType + ";charset=" + encoding;
            response.setContentType(fullContentType);
            if (noCache) {
                response.setHeader("Pragma", "No-cache");
                response.setHeader("Cache-Control", "no-cache");
                response.setDateHeader("Expires", 0);
            }
            response.setStatus(status);
            response.getWriter().write(content);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 输出js代码片断到客户端, 格式 fcallback(JSONObject)<br>
     * 默认的方法名是 fcallback
     *
     * @param request
     * @param response
     * @param jstr
     *            JSON串
     */
    public void renderJsonp(HttpServletRequest request, HttpServletResponse response, String jstr) {
        String callBack = RequestUtil.getRequestString(request, "fcallback", "fcallback");
        renderJson(response, callBack + "(" + jstr + ")");
    }

    /**
     * 输出js代码片断到客户端, 支持fcallback参数
     *
     * @param response
     * @param jsonStr
     */
    public void renderJson(HttpServletRequest request, HttpServletResponse response, String jsonStr) {
        String callBack = RequestUtil.getRequestString(request, "fcallback", null);

        if(StringUtils.trimToNull(callBack) == null){
            renderJson(response, jsonStr);
        }else{
            renderJson(response, callBack + "(" + jsonStr + ")");
        }
    }

    /**
     * 直接输出JSON
     *
     * @param string
     *            json字符串.
     */
    private void renderJson(HttpServletResponse response,String string, final String... headers) {
        render(response, "application/json", string, headers);
    }

    protected Long getUserId(){
        return 40L;
    }
}
