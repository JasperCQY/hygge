package com.szyooge.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.szyooge.util.CryptUtil;

@Controller
public class DefaultController {
    private static Logger logger = LoggerFactory.getLogger(DefaultController.class);
    
    @RequestMapping("/")
    public String index() {
        return "index";
    }
    
    @RequestMapping("/wxaccess")
    public void wxAccess(HttpServletRequest request, HttpServletResponse response) {
        String token = "yooge";
        
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        String sha1 = token + timestamp + nonce;
        logger.info("token:" + token);
        logger.info("timestamp:" + timestamp);
        logger.info("nonce:" + nonce);
        logger.info("echostr:" + echostr);
        logger.info("signature：" + signature);
        sha1 = CryptUtil.SHA1ToHex(sha1);
        
        if (sha1 != null && sha1.equals(signature)) {
            // 消息来自微信
            logger.info("消息来自微信");
            try {
                response.getWriter().write("{\"echostr\":\"" + echostr + "\"}");
            } catch (IOException e) {
                logger.error("response输出流出异常啦", e);
            }
        } else {
            logger.info("消息不是来自微信");
        }
    }
}
