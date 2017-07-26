package com.szyooge.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.szyooge.config.WeChatConf;
import com.szyooge.util.CryptUtil;
import com.szyooge.util.SortUtil;
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
    
    @Autowired
    private WeChatConf weChatConf;
    
    @RequestMapping("/wechatpush")
    public void wxAccess(HttpServletRequest request, HttpServletResponse response) {
        String token = weChatConf.getAccessToken();
        
        String signature = request.getParameter("signature");
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
            responseString(response, echostr);
        } else {
            logger.info("消息不是来自微信");
        }
    }
}
