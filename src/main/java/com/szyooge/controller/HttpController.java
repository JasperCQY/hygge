package com.szyooge.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
     * HTTP请求（只有一个JSON字符串参数，其他参数拼凑在URL中）  <br/>
     *   URL  放在参数request.getParameter("url")中  <br/>
     *   data 放在参数request.getParameterMap()剔除url
     * @author quanyou.chen
     * @date: 2017年7月26日 下午5:19:21
     * @param request
     * @param response
     */
    @RequestMapping(value = "/wxHttpGet")
    public void wxHttpGet(HttpServletRequest request, HttpServletResponse response) {
        String jsonData = request.getParameter("jsonData");
        if (StringUtil.isEmpty(jsonData)) {
            String accessTokenJson = (String) request.getAttribute("access_token_json");
            if (StringUtil.isNotNEmpty(accessTokenJson)) {
                responseString(response, accessTokenJson);
                return;
            }
        }
        String url = extractWeChatUrl(request);
        responseString(response, HttpUtil.wxSendGet(url, jsonData, CharSet.UTF8));
    }
    
    @RequestMapping(value = "/wxHttpPost")
    public void wxHttpPost(HttpServletRequest request, HttpServletResponse response) {
        String jsonData = request.getParameter("jsonData");
        if (StringUtil.isEmpty(jsonData)) {
            String accessTokenJson = (String) request.getAttribute("access_token_json");
            if (StringUtil.isNotNEmpty(accessTokenJson)) {
                responseString(response, accessTokenJson);
                return;
            }
        }
        String url = extractWeChatUrl(request);
        responseString(response, HttpUtil.wxSendPost(url, jsonData, CharSet.UTF8));
    }
    
    /**
     * 提取url
     * @author quanyou.chen
     * @date: 2017年7月27日 上午10:42:20
     * @param request
     * @return
     */
    private String extractWeChatUrl(HttpServletRequest request) {
        StringBuilder queryStr = new StringBuilder(request.getParameter("url"));
        if (queryStr.indexOf("?") > 0) {
            queryStr.append('&');
        } else {
            queryStr.append('?');
        }
        // access_token
        queryStr.append("access_token=");
        queryStr.append(request.getAttribute("access_token"));
        queryStr.append('&');
        String name = null;
        for (Enumeration<String> e = request.getParameterNames(); e.hasMoreElements();) {
            name = e.nextElement();
            if (name.equals("jsonData") || name.equals("url")) {
                continue;
            }
            queryStr.append(name);
            queryStr.append('=');
            queryStr.append(request.getParameter(name));
            queryStr.append('&');
        }
        queryStr.setLength(queryStr.length() - 1);
        return queryStr.toString();
    }
}
