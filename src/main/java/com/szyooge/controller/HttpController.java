package com.szyooge.controller;

import java.io.IOException;
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

@Controller
public class HttpController {
    @RequestMapping(value = "/httpGet")
    public void httpGet(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = new HashMap<String, String>();
        String name = null;
        for (Enumeration<String> e = request.getParameterNames(); e.hasMoreElements();) {
            name = e.nextElement();
            params.put(name, request.getParameter(name));
        }
        String url = params.remove("url");
        try {
            response.getWriter().write(HttpUtil.sendGet(url, params, CharSet.UTF8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @RequestMapping(value = "/httpPost")
    public void httpPost(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = new HashMap<String, String>();
        String name = null;
        for (Enumeration<String> e = request.getParameterNames(); e.hasMoreElements();) {
            name = e.nextElement();
            params.put(name, request.getParameter(name));
        }
        String url = params.remove("url");
        try {
            response.getWriter().write(HttpUtil.sendPost(url, params, CharSet.UTF8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value = "/wxHttpGet")
    public void wxHttpGet(HttpServletRequest request, HttpServletResponse response) {
        String jsonData = request.getParameter("jsonData");
        if(StringUtil.isEmpty(jsonData)) {
            httpGet(request,response);
        } else {
            String url = request.getParameter("url");
            try {
                response.getWriter().write(HttpUtil.wxSendGet(url, jsonData, CharSet.UTF8));
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }
    }
    
    @RequestMapping(value = "/wxHttpPost")
    public void wxHttpPost(HttpServletRequest request, HttpServletResponse response) {
        String jsonData = request.getParameter("jsonData");
        if(StringUtil.isEmpty(jsonData)) {
            httpPost(request,response);
        } else {
            String url = request.getParameter("url");
            try {
                response.getWriter().write(HttpUtil.wxSendPost(url, jsonData, CharSet.UTF8));
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }
    }
}
