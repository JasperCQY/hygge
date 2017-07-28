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
}
