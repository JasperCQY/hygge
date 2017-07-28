package com.szyooge.wechat.service;

public interface WeChatPushService {
    /**
     * 处理微信消息
     * @author quanyou.chen
     * @date: 2017年7月27日 下午3:56:13
     * @param wxMsg 微信消息
     * @return 处理结果
     */
    public String wxAccess(String wxMsg);
}
