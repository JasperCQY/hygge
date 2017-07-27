package com.szyooge.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.szyooge.service.WeChatPushService;
import com.szyooge.util.CryptUtil;
import com.szyooge.util.MDC_LOG;
import com.szyooge.util.SortUtil;
import com.szyooge.util.StringUtil;

/**
 * 微信推送消息
 * @ClassName: WeChatPushController
 * @author quanyou.chen
 * @date: 2017年7月26日 上午10:55:31
 * @version  v 1.0
 */
@Controller
public class WeChatPushController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(WeChatPushController.class);
    
    @Value("${wechat.config.token}")
    private String token;
    
    @Autowired
    private WeChatPushService weChatPushService;
    
    @RequestMapping("/wechatpush")
    public void wxAccess(HttpServletRequest request, HttpServletResponse response) {
        MDC_LOG.add();
        String signature = request.getParameter("signature");
        if (StringUtil.isNEmpty(signature)) {
            logger.warn("微信推送数据不合法（获取不到【signature】）。");
            return;
        }
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        String sha1 = SortUtil.dictionarySort(nonce, timestamp, token);
        logger.debug("token:" + token);
        logger.info("timestamp:" + timestamp);
        logger.info("nonce:" + nonce);
        logger.info("echostr:" + echostr);
        logger.info("signature：" + signature);
        logger.info("字典顺序排序:" + sha1);
        sha1 = CryptUtil.SHA1ToHex(sha1);
        
        if (sha1 != null && sha1.equals(signature)) {
            // 消息来自微信
            logger.info("消息来自微信");
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(request.getInputStream()));
                // 微信推送部分消息
                String resultPart = in.readLine();
                // 微信推送消息
                StringBuilder wxMsg = new StringBuilder();
                // 拼凑消息
                while(resultPart != null) {
                    wxMsg.append(resultPart);
                    resultPart = in.readLine();
                }
                // 打印微信推送消息
                logger.info(wxMsg.toString());
                if(echostr == null) {
                    echostr = weChatPushService.wxAccess(wxMsg.toString());
                }
            } catch (IOException e) {
                logger.info("读取微信数据失败",e);
            } finally {
                if(in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        logger.info("读取微信数据失败",e);
                    }
                }
            }
            responseString(response, echostr);
        } else {
            logger.info("消息不是来自微信");
        }
        MDC_LOG.remove();
    }
}
