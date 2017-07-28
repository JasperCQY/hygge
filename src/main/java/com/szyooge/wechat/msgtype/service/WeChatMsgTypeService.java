package com.szyooge.wechat.msgtype.service;

import java.util.Map;

/**
 * 微信消息类型处理
 * @ClassName: WeChatMsgTypeService
 * @author quanyou.chen
 * @date: 2017年7月28日 上午9:21:50
 * @version  v 1.0
 */
public interface WeChatMsgTypeService {
    /**
     * 微信消息处理
     * @author quanyou.chen
     * @date: 2017年7月28日 上午9:24:02
     * @param wxMap
     * @return
     */
    String msg(Map<String,String> wxMap);
}
