package com.szyooge.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.szyooge.constant.CharSet;

import net.sf.json.JSONObject;
import net.sf.json.JSONArray;

public abstract class BaseController {
    private static Logger logger = LoggerFactory.getLogger(BaseController.class);
    
    public void responseString(HttpServletResponse response, String str){
        try {
            response.reset();
            response.setCharacterEncoding(CharSet.UTF8);
            response.getWriter().write(str);
        } catch (IOException e) {
            logger.error("response输出流出异常啦", e);
        }
    }
    public void responseObject(HttpServletResponse response, Object obj){
        try {
            response.reset();
            response.setCharacterEncoding(CharSet.UTF8);
            response.getWriter().write(JSONObject.fromObject(obj).toString());
        } catch (IOException e) {
            logger.error("response输出流出异常啦", e);
        }
    }
    
    public void responseArray(HttpServletResponse response, Object obj){
        try {
            response.reset();
            response.setCharacterEncoding(CharSet.UTF8);
            response.getWriter().write(JSONArray.fromObject(obj).toString());
        } catch (IOException e) {
            logger.error("response输出流出异常啦", e);
        }
    }
}
