package com.szyooge.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.szyooge.constant.WeChatXmlConst;
import com.szyooge.service.WeChatPushService;
import com.szyooge.util.XmlUtil;

@Service
public class WeChatPushImpl implements WeChatPushService {
    
    private static Logger logger = LoggerFactory.getLogger(WeChatPushImpl.class);
    
    @Override
    public String wxAccess(String wxMsg) {
        String result = null;
        Map<String, String> wxMap = XmlUtil.xmlStrToMap(wxMsg);
        String msgType = wxMap.get(WeChatXmlConst.MsgType);
        switch (msgType) {
            case WeChatXmlConst.IMsgType.event:
                result = eventMsg(wxMap);
                break;
            case WeChatXmlConst.IMsgType.text:
                result = textMsg(wxMap);
                break;
            case WeChatXmlConst.IMsgType.image:
                result = imageMsg(wxMap);
                break;
            case WeChatXmlConst.IMsgType.voice:
                result = voiceMsg(wxMap);
                break;
            case WeChatXmlConst.IMsgType.video:
                result = videoMsg(wxMap);
                break;
            case WeChatXmlConst.IMsgType.location:
                result = locationMsg(wxMap);
                break;
            case WeChatXmlConst.IMsgType.link:
                result = linkMsg(wxMap);
                break;
            default:
                break;
        }
        return result;
    }
    
    private String textMsg(Map<String, String> wxMap) {
        String replyXmlStr = null;
        Map<String, String> replyMap = new HashMap<String, String>(wxMap);
        Resource resource = new ClassPathResource("replyXml/text.xml");
        InputStream in = null;
        try {
            in = resource.getInputStream();
            
            replyMap.put(WeChatXmlConst.FromUserName, wxMap.get(WeChatXmlConst.ToUserName));
            replyMap.put(WeChatXmlConst.ToUserName, wxMap.get(WeChatXmlConst.FromUserName));
            replyMap.put(WeChatXmlConst.CreateTime, String.valueOf(new Date().getTime()));
            
            replyXmlStr = XmlUtil.mapToXmlStr(replyMap, in);
        } catch (IOException e) {
            logger.error("获取资源文件流失败", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("关闭资源文件流失败", e);
                }
            }
        }
        
        return replyXmlStr;
    }
    
    private String linkMsg(Map<String, String> wxMap) {
        return null;
    }
    
    private String locationMsg(Map<String, String> wxMap) {
        return null;
    }
    
    private String videoMsg(Map<String, String> wxMap) {
        return null;
    }
    
    private String voiceMsg(Map<String, String> wxMap) {
        return null;
    }
    
    private String imageMsg(Map<String, String> wxMap) {
        return null;
    }
    
    private String eventMsg(Map<String, String> wxMap) {
        return null;
    }
    
}
