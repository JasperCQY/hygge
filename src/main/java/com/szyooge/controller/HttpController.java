package com.szyooge.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.szyooge.constant.CharSet;
import com.szyooge.util.HttpUtil;
import com.szyooge.util.StringUtil;

/**
 * HTTP请求类（支持HTTPS）
 * @ClassName: HttpController
 * @author quanyou.chen
 * @date: 2017年7月26日 下午5:18:49
 * @version  v 1.0
 */
@Controller
public class HttpController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(HttpController.class);
    
    /**
     * HTTP请求  <br/>
     *   URL  放在参数request.getParameter("url")中  <br/>
     *   data 放在参数request.getParameterMap()剔除url
     * @author quanyou.chen
     * @date: 2017年7月26日 下午5:19:21
     * @param request
     * @param response
     */
    @RequestMapping(value = "/httpGet")
    public void httpGet(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = new HashMap<String, String>();
        String name = null;
        for (Enumeration<String> e = request.getParameterNames(); e.hasMoreElements();) {
            name = e.nextElement();
            params.put(name, request.getParameter(name));
        }
        String url = params.remove("url");
        responseString(response, HttpUtil.sendGet(url, params, CharSet.UTF8));
    }
    
    /**
     * HTTP请求  <br/>
     *   URL  放在参数request.getParameter("url")中  <br/>
     *   data 放在参数request.getParameterMap()剔除url
     * @author quanyou.chen
     * @date: 2017年7月26日 下午5:19:21
     * @param request
     * @param response
     */
    @RequestMapping(value = "/httpPost")
    public void httpPost(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = new HashMap<String, String>();
        String name = null;
        for (Enumeration<String> e = request.getParameterNames(); e.hasMoreElements();) {
            name = e.nextElement();
            params.put(name, request.getParameter(name));
        }
        String url = params.remove("url");
        responseString(response, HttpUtil.sendPost(url, params, CharSet.UTF8));
    }
    
    /**
     * HTTP请求  <br/>
     *   URL  放在参数request.getParameter("url")中  <br/>
     *   data 放在参数request.getParameterMap()剔除url、payload
     * @author quanyou.chen
     * @date: 2017年7月26日 下午5:19:21
     * @param request
     * @param response
     */
    @RequestMapping(value = "/request")
    public void request(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> data = new HashMap<String, String>();
        String name = null;
        for (Enumeration<String> e = request.getParameterNames(); e.hasMoreElements();) {
            name = e.nextElement();
            data.put(name, request.getParameter(name));
        }
        logger.info("请求数据：" + data);
        Map<String, String> params = new HashMap<String, String>();
        params.put("URL", data.remove("url"));
        String payload = data.remove("payload");
        if (StringUtil.isNotNEmpty(payload)) {
            params.put("Payload", payload);
        }
        String method = data.remove("method");
        if (StringUtil.isNotNEmpty(method)) {
            params.put("Method", method);
        }
        String cookie = data.remove("cookie");
        if (StringUtil.isNotNEmpty(cookie)) {
            params.put("Cookie", cookie);
        }
        responseObject(response, HttpUtil.request(params, data, CharSet.UTF8));
    }
}
