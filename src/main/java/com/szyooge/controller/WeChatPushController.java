package com.szyooge.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 微信推送消息
 * @ClassName: WeChatPushController
 * @author quanyou.chen
 * @date: 2017年7月26日 上午10:55:31
 * @version  v 1.0
 */
@Controller("wechatpush")
public class WeChatPushController {
    @RequestMapping(value = "/menuclick")
    public void menuClick(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(request.getQueryString());
    }
}
