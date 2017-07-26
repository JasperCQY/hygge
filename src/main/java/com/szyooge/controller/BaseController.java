package com.szyooge.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.szyooge.constant.CharSet;

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
    public void responseJson(HttpServletResponse response, Object obj){
        response.reset();
        response.setCharacterEncoding(CharSet.UTF8);
//        response.getWriter().write(Jsono);
    }
}
