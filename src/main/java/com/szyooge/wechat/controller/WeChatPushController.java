package com.szyooge.wechat.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.szyooge.constant.CharSet;
import com.szyooge.controller.BaseController;
import com.szyooge.util.CryptUtil;
import com.szyooge.util.HttpUtil;
import com.szyooge.util.MDC_LOG;
import com.szyooge.util.SortUtil;
import com.szyooge.util.StringUtil;
import com.szyooge.wechat.service.WeChatPushService;

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
