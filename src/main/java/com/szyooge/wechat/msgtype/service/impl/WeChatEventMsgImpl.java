package com.szyooge.wechat.msgtype.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.szyooge.wechat.constant.WeChatXmlConst;
import com.szyooge.wechat.msgtype.service.WeChatMsgTypeService;

/**
 * 微信消息事件处理
 * @ClassName: WeChatEventMsgImpl
 * @author quanyou.chen
 * @date: 2017年7月28日 上午11:07:48
 * @version  v 1.0
 */
@Service(WeChatXmlConst.IMsgType.event)
public class WeChatEventMsgImpl implements WeChatMsgTypeService {
    
    private static Logger logger = LoggerFactory.getLogger(WeChatEventMsgImpl.class);
    
    @Autowired
    private ApplicationContext context;
    
    @Override
    public String msg(Map<String, String> wxMap) {
        String event = wxMap.get(WeChatXmlConst.Event);
        logger.info("微信消息事件类型：" + event);
        WeChatMsgTypeService weChatMsgTypeService = (WeChatMsgTypeService) context.getBean(event);
        return weChatMsgTypeService.msg(wxMap);
    }
    
}
